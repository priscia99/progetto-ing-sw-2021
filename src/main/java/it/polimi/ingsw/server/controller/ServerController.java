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
import it.polimi.ingsw.server.model.player_board.PlayerBoard;
import it.polimi.ingsw.server.model.player_board.storage.Warehouse;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.utils.CustomRunnable;

import java.security.spec.ECField;
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

    private void tryApplyBackup(){
            try {
                backupManager.applyBackup();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

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

    public void giveInitialAssets() throws Exception {
        game.setFirstPlayer();
        game.giveLeaderCardsToPlayers();
        game.giveInitialResources();
    }

    private void throwOnlyValidationExceptions(CustomRunnable validation) throws ValidationException {
        try{
            validation.tryRun();
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
    }

    private void validateBuyDevelopmentCard(String cardId, int deckIndex, ConsumeTarget toConsume) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    if(currentPlayer.hasDoneMainAction()) throw new Exception("You have already done main action this turn!");
                    DevelopmentCard toBuy = game.getCardMarket().getCardById(cardId);
                    ResourceRequirement requirement = (ResourceRequirement) toBuy.getRequirement();
                    requirement.applyDiscounts(game.getCurrentPlayer().getDiscounts());
                    if(!requirement.isFulfilled(toConsume)) throw new Exception("Wrong resources to buy card!");
                    if(!currentPlayer.canConsume(toConsume)) throw new Exception("You do not own resources selected!");
                    if(!currentPlayer.canAddDevelopmentCard(toBuy, deckIndex)) throw new Exception("You can't add development card to selected deck!");
                }
        );
    }

    public void buyDevelopmentCard(String cardId, int deckIndex, ConsumeTarget toConsume) throws Exception {
        validateBuyDevelopmentCard(cardId, deckIndex, toConsume);
        Player currentPlayer = game.getCurrentPlayer();
        DevelopmentCard cardBought = game.getCardMarket().sell(cardId, currentPlayer);
        currentPlayer.addDevelopmentCard(cardBought, deckIndex);
        currentPlayer.consumeResources(toConsume);
        currentPlayer.setHasDoneMainAction(true);
    }

    public void chooseInitialLeaders(ArrayList<String> leadersChosen, String playerUsername) throws Exception {
        game.getPlayerByUsername(playerUsername).pickedLeaderCards(leadersChosen);
        game.tryStart();
    }

    public void chooseInitialResources(ConsumeTarget resourcesToAdd, String username) throws Exception {
        game.getPlayerByUsername(username).pickedInitialResources(resourcesToAdd);
        game.tryStart();
    }

    private void validateDropLeader(String cardId) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Optional<LeaderCard> toDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().filter(c->c.getId().equals(cardId)).findFirst();
                    if(toDrop.isEmpty()) throw new Exception("Leader not found!");
                }
        );
    }

    public void dropLeaderCard(String cardId) throws Exception {
        validateDropLeader(cardId);
        game.getCurrentPlayer().dropLeaderCardById(cardId);
        game.addFaithPointsToPlayer(game.getCurrentPlayer(), 1);
    }

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
                if(!globalProductionsRequirement.isFulfilled(consumedResources)) throw new Exception("Selected resources does not correspond to those requested by productions!");
            }
        );
    }

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

    private void validatePlayLeaderCard(String cardId) throws ValidationException {
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

    public void playLeaderCard(String cardId) throws Exception {
        validatePlayLeaderCard(cardId);
        game.getCurrentPlayer().playLeaderCardById(cardId);
    }

    private void validateSwapDepots(int first, int second) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    Warehouse validateWarehouse = currentPlayer.getPlayerBoard().getWarehouse().getCopy();
                    validateWarehouse.swap(first, second);
                }
        );
    }

    public void swapDepots(int first, int second) throws Exception {
        validateSwapDepots(first, second);
        game.getCurrentPlayer().swapDepots(first, second);
    }

    public void nextTurn() throws Exception {
        game.nextTurn();
    }

    private void validateRemoveResource(ResourcePosition depot) throws ValidationException {
        throwOnlyValidationExceptions(
                ()->{
                    Player currentPlayer = game.getCurrentPlayer();
                    ResourceDepot depotSelected = currentPlayer.getPlayerBoard().getWarehouse().getDepot(depot.ordinal());
                    if(depotSelected.getQuantity()==0) throw new Exception("Cannot remove from empty depot!");
                }
        );
    }

    public void removeResource(ResourcePosition depot) throws Exception {
        validateRemoveResource(depot);
        Player currentPlayer = game.getCurrentPlayer();
        ResourceDepot depotSelected = currentPlayer.getPlayerBoard().getWarehouse().getDepot(depot.ordinal());
        ConsumeTarget target = new ConsumeTarget();
        target.put(depot, new ResourceStock(depotSelected.getResourceType(), 1));
        currentPlayer.consumeResources(target);
        game.currentPlayerDropsResource();
    }

    public void playerDied(String dead) {
        tryAction(()->game.addDead(dead));
        if(game.getCurrentPlayer().getNickname().equals(dead)){
            tryAction(this::nextTurn);
        }
    }

    public void playerRevived(String revived){
        tryAction(()->game.removeDead(revived));
    }
}
