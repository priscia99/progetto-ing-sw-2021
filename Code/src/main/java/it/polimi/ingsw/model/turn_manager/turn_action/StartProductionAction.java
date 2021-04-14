package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.market.MarbleMarket;

import java.util.List;
import java.util.Map;

public class StartProductionAction extends TurnAction{

    public StartProductionAction() {
        this.turnActionType = TurnActionType.START_PRODUCTION;
    }

    public void execute(Game currentGame, Map<String, String> params) {
        super.execute(currentGame);
        //TODO: params{newWareHouse, newStrongBox, productionsToActivate}
    }

    private void onProductionsSelected(List<ProductionEffect> productionsSelected){
        currentPlayer.getPlayerBoard().getStrongbox().removeConsumedResources();
        currentPlayer.getPlayerBoard().getWarehouse().removeConsumedResources();
        for(ProductionEffect productionToActivate : productionsSelected){
            productionToActivate.getOutPiles().forEach(
                    resourcePile -> currentPlayer.addResourcesToStrongBox(resourcePile)
            );
        }
    }
}
