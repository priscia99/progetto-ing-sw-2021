package it.polimi.ingsw.controller.turn_manager.turn_action;

import it.polimi.ingsw.server_model.card.effect.ProductionEffect;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.player_board.storage.Storage;
import it.polimi.ingsw.server_model.player_board.storage.Warehouse;
import it.polimi.ingsw.controller.turn_manager.action_params.StartProductionParams;

import java.util.ArrayList;

public class StartProductionAction extends TurnAction{

    public StartProductionAction() {
        this.turnActionType = TurnActionType.START_PRODUCTION;
    }

    public void execute(Game currentGame, StartProductionParams params) {
        //TODO: implement storage subsitutions methods
        Warehouse warehouse = params.getWarehouse();
        Storage strongbox = params.getStrongbox();
        ArrayList<ProductionEffect> productionsSelected = params.getProductionToActivate();
        for(ProductionEffect productionToActivate : productionsSelected){
            productionToActivate.getOutStocks().forEach(
                    resourcePile -> currentGame.getCurrentPlayer().addResourcesToStrongBox(resourcePile)
            );
        }
    }
}