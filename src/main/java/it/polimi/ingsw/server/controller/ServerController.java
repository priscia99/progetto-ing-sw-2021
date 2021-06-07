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
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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

    public void buyDevelopmentCard(int positionX, int positionY, int deckIndex, HashMap<ResourcePosition, ResourceStock> toConsume) {
        //TODO: use wrapper for functions, catch errors and send game.notifyError based on exception
        //if(game.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        Player currentPlayer = game.getCurrentPlayer();
        if(currentPlayer.hasDoneMainAction()) {
            game.notifyError("You have already done main action this turn!", game.getCurrentPlayer().getNickname());
            return;
        }
        DevelopmentCard toBuy = game.getCardMarket().getCard(positionX, positionY);
        ResourceRequirement requirement = (ResourceRequirement) toBuy.getRequirement();
        if(requirement.isFulfilled(toConsume)) {
            if(currentPlayer.canConsume(toConsume)){
                if(currentPlayer.canAddDevelopmentCard(toBuy, deckIndex)){
                    DevelopmentCard cardBought = game.getCardMarket().sell(positionX,positionY, currentPlayer);
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

    public void pickResources(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions) {
        if(game.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        ArrayList<Marble> selectedMarbles = game.getMarbleMarket().sell(marbleSelection);
        for(int i = 0; i<selectedMarbles.size(); i++){
            ResourceType resourceToAdd = selectedMarbles.get(i).getResourceType();
            ResourcePosition selectedPosition = positions.get(i);
            if(selectedPosition == ResourcePosition.DROPPED){
                game.currentPlayerDropsResource();
            } else {
                game.getCurrentPlayer().addResourceToDepot(resourceToAdd, selectedPosition.ordinal());
            }
        }
        game.getCurrentPlayer().setHasDoneMainAction(true);
    }

    public void startProduction(HashMap<ResourcePosition, ResourceStock> consumedResources, ArrayList<ProductionEffect> productionsToActivate) {
        if(game.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        consumedResources.keySet().forEach((resourcePosition)->{
            switch (resourcePosition){
                case DROPPED: game.currentPlayerDropsResource(); break;
                case STRONG_BOX:
                    for(int i = 0; i<consumedResources.get(resourcePosition).getQuantity(); i++){
                        game.getCurrentPlayer()
                                .getPlayerBoard()
                                .getStrongbox()
                                .removeResource(consumedResources.get(resourcePosition).getResourceType());
                    }
                    break;
                default:
                    for(int i = 0; i<consumedResources.get(resourcePosition).getQuantity(); i++){
                        game.getCurrentPlayer()
                                .getPlayerBoard()
                                .getWarehouse()
                                .removeFromDepot(resourcePosition.ordinal(), consumedResources.get(resourcePosition).getResourceType());
                    }
            }
        });
        ArrayList<ProductionEffect> productionsSelected = productionsToActivate;
        for(ProductionEffect productionToActivate : productionsSelected){
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
