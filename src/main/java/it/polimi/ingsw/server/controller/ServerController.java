package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.exceptions.ValidationException;
import it.polimi.ingsw.server.model.BackupManager;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.player_board.storage.Warehouse;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.CustomRunnable;

import java.util.ArrayList;
import java.util.Optional;

public class ServerController {

    private final Game game;
    private BackupManager backupManager;

    public ServerController(Game game){
        this.game = game;
    }

    public void setBackupManager(BackupManager manager){
        this.backupManager = manager;
    }

    /**
     * Create and save backup, manage error if thrown
     */
    private void tryCreateBackup(){
        if(game.isStarted()){
            try{
                backupManager.load(game.getBackup());
            } catch (Exception e){
                CustomLogger.getLogger().info("Error while creating game backup!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Apply backup to client, manage error if thrown
     */
    private void tryApplyBackup(){
            try {
                backupManager.applyBackup();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    /**
     * Execute action sent by client, apply backup in case of errors unknown,
     * send error to client in case of validation error
     * @param action Action to execute on game
     */
    public void tryAction(CustomRunnable action){
        tryCreateBackup();
        try{
            action.tryRun();
        } catch (ValidationException e){
            game.notifyError(e.getMessage(), game.getCurrentPlayer().getNickname());
        } catch (Exception e){
            e.printStackTrace();
            game.notifyError(e.getMessage(), game.getCurrentPlayer().getNickname());
            tryApplyBackup();
        } finally {
            tryCreateBackup();
        }
    }

    public void setupGame(ArrayList<Player> players) throws Exception {
      game.setup(players);
    }

    /**
     * Execute starting game rules
     * @throws Exception
     */
    public void giveInitialAssets() throws Exception {
        game.setFirstPlayer();
        game.giveLeaderCardsToPlayers();
        game.giveInitialResources();
    }

    /**
     * Catch exception and convert it to validation exception
     * @param validation Validation method to execute
     * @throws ValidationException
     */
    private void throwOnlyValidationExceptions(CustomRunnable validation) throws ValidationException {
        try{
            validation.tryRun();
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Validation for buy development card action
     * @param cardId ID of card to buy
     * @param deckIndex Index of deck into which insert new card
     * @param toConsume Resources to consume to buy card
     * @throws ValidationException
     */
    private void validateBuyDevelopmentCard(String cardId, int deckIndex, ConsumeTarget toConsume) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    if(currentPlayer.hasDoneMainAction()) throw new Exception("You have already done main action this turn!");
                    DevelopmentCard toBuy = game.getCardMarket().getCardById(cardId);
                    ResourceRequirement requirement = (ResourceRequirement) toBuy.getRequirement();
                    requirement.applyDiscounts(game.getCurrentPlayer().getDiscounts());
                    if(!requirement.matchRequirement(toConsume)) throw new Exception("Wrong resources to buy card!");
                    if(!currentPlayer.canConsume(toConsume)) throw new Exception("You do not own resources selected!");
                    if(!currentPlayer.canAddDevelopmentCard(toBuy, deckIndex)) throw new Exception("You can't add development card to selected deck!");
                }
        );
    }

    /**
     * Execute buy development card action on game
     * @param cardId ID of card to buy
     * @param deckIndex Index of deck into which insert new card
     * @param toConsume Resources to consume to buy card
     * @throws Exception
     */
    public void buyDevelopmentCard(String cardId, int deckIndex, ConsumeTarget toConsume) throws Exception {
        validateBuyDevelopmentCard(cardId, deckIndex, toConsume);
        Player currentPlayer = game.getCurrentPlayer();
        DevelopmentCard cardBought = game.getCardMarket().sell(cardId, currentPlayer);
        currentPlayer.addDevelopmentCard(cardBought, deckIndex);
        currentPlayer.consumeResources(toConsume);
        currentPlayer.setHasDoneMainAction(true);
    }

    /**
     * Execute choose initial leader action on game
     * @param leadersChosen List of IDs of leader cards chosen
     * @param playerUsername Username who made the choice
     * @throws Exception
     */
    public void chooseInitialLeaders(ArrayList<String> leadersChosen, String playerUsername) throws Exception {
        game.getPlayerByUsername(playerUsername).pickedLeaderCards(leadersChosen);
        game.tryStart();
    }

    /**
     * Execute initial resource action on game
     * @param resourcesToAdd Resources and position into which add them
     * @param username Username who made the choice
     * @throws Exception
     */
    public void chooseInitialResources(ConsumeTarget resourcesToAdd, String username) throws Exception {
        game.getPlayerByUsername(username).pickedInitialResources(resourcesToAdd);
        game.tryStart();
    }

    /**
     * Validation method for drop leader card action
     * @param cardId ID of leader card to drop
     * @throws ValidationException
     */
    private void validateDropLeader(String cardId) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Optional<LeaderCard> toDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().filter(c->c.getId().equals(cardId)).findFirst();
                    if(toDrop.isEmpty()) throw new Exception("Leader not found!");
                }
        );
    }

    /**
     * Execute drop leader card action on game
     * @param cardId ID of leader card to drop
     * @throws Exception
     */
    public void dropLeaderCard(String cardId) throws Exception {
        validateDropLeader(cardId);
        game.getCurrentPlayer().dropLeaderCardById(cardId);
        game.addFaithPointsToPlayer(game.getCurrentPlayer(), 1);
    }

    /**
     * Validation method for pick resources action
     * @param marbleSelection marble market selection
     * @param positions Position into which insert new resources
     * @param converted Conversions made in case of leader change effect and white marble
     * @throws ValidationException
     */
    private void validatePickResources(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions,  ArrayList<ResourceType> converted) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    int posIndex = 0;
                    int convIndex = 0;
                    if(currentPlayer.hasDoneMainAction()) throw new Exception("You have already done main action this turn!");
                    ArrayList<Marble> validateSelection = game.getMarbleMarket().getSelectedMarbles(marbleSelection);
                    Warehouse validateWarehouse = currentPlayer.getPlayerBoard().getWarehouse().getCopy();
                    ArrayList<ResourceDepot> validateLeaderDepots = currentPlayer.getPlayerBoard().getAdditionalDepotsCopy();
                    for (Marble marble : validateSelection) {
                        ResourceType resourceToAdd = marble.getResourceType();
                        if (resourceToAdd.equals(ResourceType.FAITH)) continue;
                        if (resourceToAdd.equals(ResourceType.BLANK) && converted.isEmpty()) continue;
                        ResourcePosition selectedPosition = positions.get(posIndex);
                        posIndex++;
                        if (selectedPosition != ResourcePosition.DROPPED) {
                            if (selectedPosition == ResourcePosition.STRONG_BOX)
                                throw new Exception("Cannot insert resources in strongbox!");
                            if (resourceToAdd.equals(ResourceType.BLANK)) {
                                resourceToAdd = converted.get(convIndex);
                                convIndex++;
                            }

                            switch (selectedPosition) {
                                case FIRST_LEADER_DEPOT -> validateLeaderDepots.get(0).incrementResource(resourceToAdd);
                                case SECOND_LEADER_DEPOT -> validateLeaderDepots.get(1).incrementResource(resourceToAdd);
                                default -> validateWarehouse.addToDepot(selectedPosition.ordinal(), resourceToAdd);
                            }
                        }
                    }
                }
        );
    }

    /**
     * Execute pick resources action on game
     * @param marbleSelection marble market selection
     * @param positions Position into which insert new resources
     * @param converted Conversions made in case of leader change effect and white marble
     * @throws Exception
     */
    public void pickResources(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions,  ArrayList<ResourceType> converted) throws Exception {
        validatePickResources(marbleSelection, positions, converted);
        Player currentPlayer = game.getCurrentPlayer();
        int posIndex = 0;
        int convIndex= 0;
        ArrayList<Marble> selectedMarbles = game.getMarbleMarket().sell(marbleSelection);
        ArrayList<ResourceType> typesSelected = new ArrayList<>();
        ArrayList<ResourcePosition> depotsSelected = new ArrayList<>();
        for (Marble selectedMarble : selectedMarbles) {
            ResourceType resourceToAdd = selectedMarble.getResourceType();
            if (resourceToAdd == ResourceType.FAITH) {
                game.addFaithPointsToPlayer(game.getCurrentPlayer(), 1);
            } else {
                if (resourceToAdd.equals(ResourceType.BLANK) && converted.isEmpty()) continue;
                ResourcePosition selectedPosition = positions.get(posIndex);
                posIndex++;
                if (selectedPosition == ResourcePosition.DROPPED) {
                    game.currentPlayerDropsResource();
                } else {
                    if (resourceToAdd.equals(ResourceType.BLANK)) {
                        resourceToAdd = converted.get(convIndex);
                        convIndex++;
                    }
                    typesSelected.add(resourceToAdd);
                    depotsSelected.add(selectedPosition);
                }
            }
        }
        currentPlayer.addAllResourceToDepots(typesSelected, depotsSelected);
        currentPlayer.setHasDoneMainAction(true);
    }

    /**
     * Validation method for start production action
     * @param consumedResources Resources to use as input for productions
     * @param productionsToActivate List of production effects to activate
     * @throws ValidationException
     */
    private void validateStartProduction(ConsumeTarget consumedResources, ArrayList<ProductionEffect> productionsToActivate) throws ValidationException {
        throwOnlyValidationExceptions(
            ()->{
                Player currentPlayer = game.getCurrentPlayer();
                if(currentPlayer.hasDoneMainAction()) throw new Exception("You have already done main action this turn!");
                ArrayList<ResourceStock> inStocks = new ArrayList<>();
                for (ProductionEffect productionEffect : productionsToActivate) {
                    inStocks.addAll(productionEffect.getInStocks());
                }
                ResourceRequirement globalProductionsRequirement = ResourceRequirement.merge(inStocks);
                if(!currentPlayer.canConsume(consumedResources)) throw new Exception("You can't consume resources defined, check them and try again!");
                if(!globalProductionsRequirement.matchRequirement(consumedResources)) throw new Exception("Selected resources does not correspond to those requested by productions!");
            }
        );
    }

    /**
     * Execute start production action on game
     * @param consumedResources Resources to use as input for productions
     * @param productionsToActivate List of production effects to activate
     * @throws Exception
     */
    public void startProduction(ConsumeTarget consumedResources, ArrayList<ProductionEffect> productionsToActivate) throws Exception {
        validateStartProduction(consumedResources, productionsToActivate);
        Player currentPlayer = game.getCurrentPlayer();
        currentPlayer.consumeResources(consumedResources);
        for(ProductionEffect productionToActivate : productionsToActivate){
            for(ResourceStock stock : productionToActivate.getOutStocks()){
                if(stock.getResourceType().equals(ResourceType.FAITH)){
                    game.addFaithPointsToPlayer(currentPlayer, stock.getQuantity());
                } else {
                    game.getCurrentPlayer().addResourcesToStrongBox(stock);
                }
            }
        }
        game.getCurrentPlayer().setHasDoneMainAction(true);
    }

    /**
     * Validation method for play leader card action
     * @param cardId ID of leader cart to activate
     * @throws ValidationException
     */
    private void validatActivateLeaderCard(String cardId) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Optional<LeaderCard> toActivate = game.getCurrentPlayer()
                            .getPlayerBoard().getLeaderCardsDeck().getLeaderCards()
                            .stream().filter(c->c.getId().equals(cardId)).findFirst();
                    if(toActivate.isEmpty()) throw new Exception("Leader not found!");
                    if(!toActivate.get().getRequirement().isFulfilled(game.getCurrentPlayer())) throw new Exception("Leader requirements are not fulfilled!");
                }
        );
    }

    /**
     * Execute play leader card on game
     * @param cardId ID of leader cart to activate
     * @throws Exception
     */
    public void activateLeaderCard(String cardId) throws Exception {
        validatActivateLeaderCard(cardId);
        game.getCurrentPlayer().playLeaderCardById(cardId);
    }

    /**
     * Validation method for swap depots action
     * @param first Index of first depot to swap
     * @param second Index of second depot to swap
     * @throws ValidationException
     */
    private void validateSwapDepots(int first, int second) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    Warehouse validateWarehouse = currentPlayer.getPlayerBoard().getWarehouse().getCopy();
                    validateWarehouse.swap(first, second);
                }
        );
    }

    /**
     * Execute swap depots on game
     * @param first Index of first depot to swap
     * @param second Index of second depot to swap
     * @throws Exception
     */
    public void swapDepots(int first, int second) throws Exception {
        validateSwapDepots(first, second);
        game.getCurrentPlayer().swapDepots(first, second);
    }

    /**
     * Execute next turn on game
     * @throws Exception
     */
    public void nextTurn() throws Exception {
        game.nextTurn();
    }

    /**
     * Validation for remove resources action
     * @param depot Depot from which remove resource
     * @throws ValidationException
     */
    private void validateRemoveResource(ResourcePosition depot) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    ResourceDepot depotSelected = currentPlayer.getPlayerBoard().getWarehouse().getDepot(depot.ordinal());
                    if(depotSelected.getQuantity()==0) throw new Exception("Cannot remove from empty depot!");
                }
        );
    }

    /**
     * Execute remove resources action on game
     * @param depot Depot from which remove resource
     * @throws Exception
     */
    public void removeResource(ResourcePosition depot) throws Exception {
        validateRemoveResource(depot);
        Player currentPlayer = game.getCurrentPlayer();
        ResourceDepot depotSelected = currentPlayer.getPlayerBoard().getWarehouse().getDepot(depot.ordinal());
        ConsumeTarget target = new ConsumeTarget();
        target.put(depot, new ResourceStock(depotSelected.getResourceType(), 1));
        currentPlayer.consumeResources(target);
        game.currentPlayerDropsResource();
    }

    /**
     * Manage player disconnection
     * @param disconnected
     */
    public void playerDisconnected(String disconnected) {
        tryAction(()->game.addDisconnected(disconnected));
        if(game.getCurrentPlayer().getNickname().equals(disconnected)){
            if(game.getPlayers().size() != game.countDisconnected()){
                tryAction(this::nextTurn);
            }
        }
    }

    /**
     * Manage player reconnection
     * @param connected
     */
    public void playerReconnected(String connected){
        tryAction(()->game.removeDisconnected(connected));
    }
}
