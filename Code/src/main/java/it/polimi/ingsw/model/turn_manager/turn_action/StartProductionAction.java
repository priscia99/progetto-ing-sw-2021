package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.player_board.storage.Warehouse;

import java.util.List;
import java.util.Map;

public class StartProductionAction extends TurnAction{

    public StartProductionAction() {
        this.turnActionType = TurnActionType.START_PRODUCTION;
    }

    public void execute(Game currentGame, Map<String, Object> params) {
        //TODO: params{newWareHouse, newStrongBox, productionsToActivate}
        //TODO: implement storage subsitutions methods
        Warehouse warehouse = (Warehouse) params.get("warehouse");
        Strongbox strongbox = (Strongbox) params.get("strongbox");
        List<ProductionEffect> productionsSelected = (List<ProductionEffect>) params.get("productions");
        for(ProductionEffect productionToActivate : productionsSelected){
            productionToActivate.getOutPiles().forEach(
                    resourcePile -> currentGame.getCurrentPlayer().addResourcesToStrongBox(resourcePile)
            );
        }
    }
}
