package v2.server.controller.action;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.resource.ResourcePosition;
import it.polimi.ingsw.model.resource.ResourceStock;

import java.util.ArrayList;
import java.util.HashMap;

public class StartProductionAction extends Action {
    HashMap<ResourcePosition, ResourceStock> consumedResources;
    ArrayList<ProductionEffect> productionsToActivate;

    public StartProductionAction(HashMap<ResourcePosition, ResourceStock> consumed,
                                 ArrayList<ProductionEffect> productions) {
        consumedResources = consumed;
        productionsToActivate = productions;
    }

    public void execute(Game currentGame) {
        if(currentGame.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        consumedResources.keySet().forEach((resourcePosition)->{
            switch (resourcePosition){
                case DROPPED: currentGame.currentPlayerDropsResource(); break;
                case STRONG_BOX:
                    for(int i = 0; i<consumedResources.get(resourcePosition).getQuantity(); i++){
                        currentGame.getCurrentPlayer()
                                .getPlayerBoard()
                                .getStrongbox()
                                .removeResource(consumedResources.get(resourcePosition).getResourceType());
                    }
                    break;
                default:
                    for(int i = 0; i<consumedResources.get(resourcePosition).getQuantity(); i++){
                        currentGame.getCurrentPlayer()
                                .getPlayerBoard()
                                .getWarehouse()
                                .removeFromDepot(resourcePosition.ordinal(), consumedResources.get(resourcePosition).getResourceType());
                    }
            }
        });
        ArrayList<ProductionEffect> productionsSelected = productionsToActivate;
        for(ProductionEffect productionToActivate : productionsSelected){
            productionToActivate.getOutStocks().forEach(
                    resourcePile -> currentGame.getCurrentPlayer().addResourcesToStrongBox(resourcePile)
            );
        }
        currentGame.getCurrentPlayer().setHasDoneMainAction(true);
    }
}
