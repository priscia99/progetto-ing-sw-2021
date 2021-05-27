package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.Controller;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerController extends Controller {
    private Game game;

    public ServerController(Game game){
        this.game = game;
    }

    public void setupGame(ArrayList<Player> players){
        game.setup(players);
    }

    public void buyDevelopmentCard(int positionX, int positionY, int deckIndex) {
        if(game.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        DevelopmentCard cardBought = game.getCardMarket().sell(positionX,positionY, game.getCurrentPlayer());
        game.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(cardBought, deckIndex);
        game.getCurrentPlayer().setHasDoneMainAction(true);
    }

    public void chooseInitialLeaders(ArrayList<LeaderCard> leadersChosen, String playerUsername) {
        game.getPlayerByUsername(playerUsername).pickedLeaderCards(leadersChosen);
        game.tryStart();
    }

    public void chooseInitialResources(HashMap<ResourcePosition, ResourceType> resourcesToAdd, String username) {
        game.getPlayerByUsername(username).pickedInitialResources(resourcesToAdd);
        game.tryStart();
    }
    public void dropLeaderCard(String cardId) {
        game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().removeLeaderCardById(cardId);
        game.getCurrentPlayer().addFaithPoints(1);
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
        game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().activateLeaderCardById(cardId);
    }
}
