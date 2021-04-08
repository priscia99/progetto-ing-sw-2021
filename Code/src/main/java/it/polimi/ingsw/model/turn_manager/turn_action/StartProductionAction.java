package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.market.MarbleMarket;

import java.util.List;

public class StartProductionAction extends TurnAction{

    public StartProductionAction() {
        this.turnActionType = TurnActionType.START_PRODUCTION;
    }

    @Override
    public void execute(Player player, Game currentGame) {
        super.execute(player, currentGame);
        //select production to activate
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
