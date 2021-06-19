package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.network.message.from_server.ExceptionMessage;
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
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ServerController {

    private final Game game;

    public ServerController(Game game){
        this.game = game;
    }

    public void tryAction(CustomRunnable action){
        try{
            action.tryRun();
        } catch (Exception e){
            CustomLogger.getLogger().severe("[PLAYER: " + game.getCurrentPlayer() + "] " + e.getMessage());
            game.notifyError(e.getMessage(), game.getCurrentPlayer().getNickname());
        }
    }

    public void setupGame(ArrayList<Player> players){
        game.setPlayers(players);
        game.setupVictoryObservations();
        game.setupLeaderCards();
        game.setupCardsMarket();
        game.setupMarbleMarket();
    }

    public void giveInitialAssets() {
        game.setFirstPlayer();
        System.out.println("Giving leader cards to players...");
        game.giveLeaderCardsToPlayers();
        System.out.println("Giving resources to players...");
        game.giveInitialResources();
    }

    public void buyDevelopmentCard(String cardId, int deckIndex, ConsumeTarget toConsume) throws Exception {
        Player currentPlayer = game.getCurrentPlayer();
        if(currentPlayer.hasDoneMainAction()) throw new Exception("You have already done main action this turn!");
        DevelopmentCard toBuy = game.getCardMarket().getCardById(cardId);
        ResourceRequirement requirement = (ResourceRequirement) toBuy.getRequirement();
        requirement.applyDiscounts(game.getCurrentPlayer().getDiscounts());
        if(!requirement.isFulfilled(toConsume)) throw new Exception("Wrong resources to buy card!");
        if(!currentPlayer.canConsume(toConsume)) throw new Exception("You do not own resources selected!");
        if(!currentPlayer.canAddDevelopmentCard(toBuy, deckIndex)) throw new Exception("You can't add development card to selected deck!");
        DevelopmentCard cardBought = game.getCardMarket().sell(cardId, currentPlayer);
        currentPlayer.addDevelopmentCard(cardBought, deckIndex);
        currentPlayer.consumeResources(toConsume);
        currentPlayer.setHasDoneMainAction(true);
    }

    public void chooseInitialLeaders(ArrayList<String> leadersChosen, String playerUsername) {
        game.getPlayerByUsername(playerUsername).pickedLeaderCards(leadersChosen);
        game.tryStart();
    }

    public void chooseInitialResources(HashMap<ResourcePosition, ResourceType> resourcesToAdd, String username) {
        game.getPlayerByUsername(username).pickedInitialResources(resourcesToAdd);
        game.tryStart();
    }
    public void dropLeaderCard(String cardId) throws Exception {
        Optional<LeaderCard> toDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().filter(c->c.getId().equals(cardId)).findFirst();
        if(toDrop.isEmpty()) throw new Exception("Leader not found!");
        game.getCurrentPlayer().dropLeaderCardById(cardId);
        game.getCurrentPlayer().addFaithPoints(1);
    }

    public void pickResources(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions,  ArrayList<ResourceType> converted) throws Exception {
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
                switch(selectedPosition){
                    case FIRST_LEADER_DEPOT: validateLeaderDepots.get(0).incrementResource(resourceToAdd);
                    case SECOND_LEADER_DEPOT: validateLeaderDepots.get(1).incrementResource(resourceToAdd);
                    default: validateWarehouse.addToDepot(selectedPosition.ordinal(), resourceToAdd);
                }
            }
        }
        posIndex = 0;
        convIndex= 0;
        ArrayList<Marble> selectedMarbles = game.getMarbleMarket().sell(marbleSelection);
        ArrayList<ResourceType> typesSelected = new ArrayList<>();
        ArrayList<ResourcePosition> depotsSelected = new ArrayList<>();
        for (Marble selectedMarble : selectedMarbles) {
            ResourceType resourceToAdd = selectedMarble.getResourceType();
            if (resourceToAdd == ResourceType.FAITH) {
                game.getCurrentPlayer().addFaithPoints(1);
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

    public void startProduction(ConsumeTarget consumedResources, ArrayList<ProductionEffect> productionsToActivate) throws Exception {
        Player currentPlayer = game.getCurrentPlayer();
        if(currentPlayer.hasDoneMainAction()) throw new Exception("You have already done main action this turn!");
        ArrayList<ResourceStock> inStocks = new ArrayList<>();
        for (ProductionEffect productionEffect : productionsToActivate) {
            inStocks.addAll(productionEffect.getInStocks());
        }
        ResourceRequirement globalProductionsRequirement = ResourceRequirement.merge(inStocks);
        if(!currentPlayer.canConsume(consumedResources)) throw new Exception("You can't consume resources defined, check them and try again!");
        if(!globalProductionsRequirement.isFulfilled(consumedResources)) throw new Exception("Selected resources does not correspond to those requested by productions!");
        currentPlayer.consumeResources(consumedResources);
        for(ProductionEffect productionToActivate : productionsToActivate){
            productionToActivate.getOutStocks().forEach(
                    stock -> {
                        if(stock.getResourceType().equals(ResourceType.FAITH)){
                            game.getCurrentPlayer().addFaithPoints(stock.getQuantity());
                        } else {
                            game.getCurrentPlayer().addResourcesToStrongBox(stock);
                        }
                    }
            );
        }
        game.getCurrentPlayer().setHasDoneMainAction(true);
    }

    public void playLeaderCard(String cardId) throws Exception {
        Optional<LeaderCard> toActivate = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().filter(c->c.getId().equals(cardId)).findFirst();
        if(toActivate.isEmpty()) throw new Exception("Leader not found!");
        if(!toActivate.get().getRequirement().isFulfilled(game.getCurrentPlayer())) throw new Exception("Leader requirements are not fulfilled!");
        game.getCurrentPlayer().playLeaderCardById(cardId);
    }

    public void swapDepots(int first, int second){
        game.getCurrentPlayer().swapDepots(first, second);
    }

    public void nextTurn(){
        game.nextTurn();
    }
}
