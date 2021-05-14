package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.server_model.card.effect.ProductionEffect;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.resource.ResourceStock;

import java.util.ArrayList;
import java.util.HashMap;

public class StartProductionAction extends Action {
    ArrayList<HashMap<ResourceStock, Integer>> consumedResources;
    ArrayList<ProductionEffect> productionsToActivate;

    public StartProductionAction(ArrayList<HashMap<ResourceStock, Integer>> consumed,
                                 ArrayList<ProductionEffect> productions) {
        consumedResources = consumed;
        productionsToActivate = productions;
    }

    public void execute(Game currentGame) {
        //TODO: implement depot consume methods
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
