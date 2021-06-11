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
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServerController {

    private final Game game;

    public ServerController(Game game){
        this.game = game;
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

    public void buyDevelopmentCard(String cardId, int deckIndex, HashMap<ResourcePosition, ResourceStock> toConsume) {
        Player currentPlayer = game.getCurrentPlayer();
        if(currentPlayer.hasDoneMainAction()) {
            game.notifyError("You have already done main action this turn!", game.getCurrentPlayer().getNickname());
            return;
        }
        DevelopmentCard toBuy = game.getCardMarket().getCardById(cardId);
        ResourceRequirement requirement = (ResourceRequirement) toBuy.getRequirement();
        requirement.applyDiscounts(game.getCurrentPlayer().getDiscounts());
        if(requirement.isFulfilled(toConsume)) {
            if(currentPlayer.canConsume(toConsume)){
                if(currentPlayer.canAddDevelopmentCard(toBuy, deckIndex)){
                    DevelopmentCard cardBought = game.getCardMarket().sell(cardId, currentPlayer);
                    currentPlayer.addDevelopmentCard(cardBought, deckIndex);
                    currentPlayer.consumeResources(toConsume);
                    currentPlayer.setHasDoneMainAction(true);
                } else {
                    game.notifyError("You can't add development card to selected deck!", currentPlayer.getNickname());
                }
            } else {
                game.notifyError("You do not own resources selected!", currentPlayer.getNickname());
            }
        } else {
            game.notifyError("Wrong resources to buy card!", currentPlayer.getNickname());
        }
    }

    public void chooseInitialLeaders(ArrayList<String> leadersChosen, String playerUsername) {
        game.getPlayerByUsername(playerUsername).pickedLeaderCards(leadersChosen);
        game.tryStart();
    }

    public void chooseInitialResources(HashMap<ResourcePosition, ResourceType> resourcesToAdd, String username) {
        game.getPlayerByUsername(username).pickedInitialResources(resourcesToAdd);
        game.tryStart();
    }
    public void dropLeaderCard(String cardId) {
        Optional<LeaderCard> toDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().filter(c->c.getId().equals(cardId)).findFirst();
        if(toDrop.isPresent()){
            game.getCurrentPlayer().dropLeaderCardById(cardId);
            game.getCurrentPlayer().addFaithPoints(1);
        } else {
            game.notifyError("Leader not found!", game.getCurrentPlayer().getNickname());
        }
    }

    public void pickResources(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions,  ArrayList<ResourceType> converted) {
        Player currentPlayer = game.getCurrentPlayer();
        int posIndex = 0;
        int convIndex = 0;
        if(currentPlayer.hasDoneMainAction()) {
            game.notifyError("You have already done main action this turn!", game.getCurrentPlayer().getNickname());
            return;
        }
        ArrayList<Marble> validateSelection = game.getMarbleMarket().getSelectedMarbles(marbleSelection);
        Warehouse validateWarehouse = currentPlayer.getPlayerBoard().getWarehouse().getCopy();
        for(int i = 0; i<validateSelection.size(); i++){
            ResourceType resourceToAdd = validateSelection.get(i).getResourceType();
            if(resourceToAdd.equals(ResourceType.FAITH)) continue;
            if(resourceToAdd.equals(ResourceType.BLANK) && converted.isEmpty()) continue;
            ResourcePosition selectedPosition = positions.get(posIndex);
            posIndex++;
            if(selectedPosition != ResourcePosition.DROPPED){
                if(selectedPosition == ResourcePosition.STRONG_BOX){
                    game.notifyError("Cannot insert resources in strongbox!", currentPlayer.getNickname());
                    return;
                }
                try{
                    if(resourceToAdd.equals(ResourceType.BLANK)){
                        validateWarehouse.addToDepot(selectedPosition.ordinal(), converted.get(convIndex));
                        convIndex++;
                    } else {
                        validateWarehouse.addToDepot(selectedPosition.ordinal(), resourceToAdd);
                    }
                } catch(Exception e){
                    game.notifyError("Cannot insert selected marbles in those position!", currentPlayer.getNickname());
                    return;
                }
            }
        }
        posIndex = 0;
        convIndex= 0;
        ArrayList<Marble> selectedMarbles = game.getMarbleMarket().sell(marbleSelection);
        for(int i = 0; i<selectedMarbles.size(); i++){
            ResourceType resourceToAdd = selectedMarbles.get(i).getResourceType();
            switch(resourceToAdd){
                case FAITH:
                    game.getCurrentPlayer().addFaithPoints(1);
                    break;
                default:
                    if(resourceToAdd.equals(ResourceType.BLANK) && converted.isEmpty()) break;
                    ResourcePosition selectedPosition = positions.get(posIndex);
                    posIndex++;
                    if(selectedPosition == ResourcePosition.DROPPED){
                        game.currentPlayerDropsResource();
                    } else {
                        if(resourceToAdd.equals(ResourceType.BLANK)){
                            currentPlayer.addResourceToDepot(converted.get(convIndex), selectedPosition.ordinal());
                            convIndex++;
                        } else {
                            currentPlayer.addResourceToDepot(resourceToAdd, selectedPosition.ordinal());
                        }
                    }
            }
        }
        currentPlayer.setHasDoneMainAction(true);
    }

    public void startProduction(HashMap<ResourcePosition, ResourceStock> consumedResources, ArrayList<ProductionEffect> productionsToActivate) {
        Player currentPlayer = game.getCurrentPlayer();
        if(currentPlayer.hasDoneMainAction()) {
            game.notifyError("You have already done main action this turn!", game.getCurrentPlayer().getNickname());
            return;
        }

        ArrayList<ResourceStock> inStocks = new ArrayList<>();
        for (ProductionEffect productionEffect : productionsToActivate) {
            inStocks.addAll(productionEffect.getInStocks());
        }
        ResourceRequirement globalProductionsRequirement = ResourceRequirement.merge(inStocks);
        if(!currentPlayer.canConsume(consumedResources)){
            game.notifyError("You can't consume resources defined, check them and try again!", currentPlayer.getNickname());
            return;
        }

        if(!globalProductionsRequirement.isFulfilled(consumedResources)){
            game.notifyError("Selected resources does not correspond to those requested by productions!", currentPlayer.getNickname());
            return;
        }

        currentPlayer.consumeResources(consumedResources);
        for(ProductionEffect productionToActivate : productionsToActivate){
            productionToActivate.getOutStocks().forEach(
                    resourcePile -> game.getCurrentPlayer().addResourcesToStrongBox(resourcePile)
            );
        }
        game.getCurrentPlayer().setHasDoneMainAction(true);
    }

    public void playLeaderCard(String cardId) {
        Optional<LeaderCard> toActivate = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().filter(c->c.getId().equals(cardId)).findFirst();
        if(toActivate.isPresent()){
            if(toActivate.get().getRequirement().isFulfilled(game.getCurrentPlayer())){
                game.getCurrentPlayer().playLeaderCardById(cardId);
            } else {
               game.notifyError("Leader requirements are not fulfilled!", game.getCurrentPlayer().getNickname());
            }
        } else {
            game.notifyError("Leader not found!", game.getCurrentPlayer().getNickname());
        }
    }

    public void swapDepots(int first, int second){
        game.getCurrentPlayer().swapDepots(first, second);
    }

    public void nextTurn(){
        game.nextTurn();
    }
}
