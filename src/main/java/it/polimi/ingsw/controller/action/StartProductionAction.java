package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.server_model.card.effect.ProductionEffect;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.resource.ResourceStock;

import java.util.ArrayList;
import java.util.HashMap;

public class StartProductionAction extends Action {
    HashMap<Integer, ResourceStock> consumedResources;
    ArrayList<ProductionEffect> productionsToActivate;

    public StartProductionAction(HashMap<Integer, ResourceStock> consumed,
                                 ArrayList<ProductionEffect> productions) {
        consumedResources = consumed;
        productionsToActivate = productions;
    }

    public void execute(Game currentGame) {
        //TODO: so far only resource from warehouse, should also be possible from strongbox
        consumedResources.keySet().forEach((depotIndex)->{
            for(int i = 0; i<consumedResources.get(depotIndex).getQuantity(); i++){
                currentGame.getCurrentPlayer()
                        .getPlayerBoard()
                        .getWarehouse()
                        .removeFromDepot(depotIndex, consumedResources.get(depotIndex).getResourceType());
            }
        });
        if(currentGame.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        ArrayList<ProductionEffect> productionsSelected = productionsToActivate;
        for(ProductionEffect productionToActivate : productionsSelected){
            productionToActivate.getOutStocks().forEach(
                    resourcePile -> currentGame.getCurrentPlayer().addResourcesToStrongBox(resourcePile)
            );
        }
        currentGame.getCurrentPlayer().setHasDoneMainAction(true);
    }
}
