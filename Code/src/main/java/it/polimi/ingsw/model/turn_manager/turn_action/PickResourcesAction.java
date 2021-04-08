package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.marble.MarbleSelection;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class PickResourcesAction extends TurnAction{
    private MarbleMarket marbleMarket;

    public PickResourcesAction() {
        this.turnActionType = TurnActionType.PICK_RESOURCES;
    }

    @Override
    public void execute(Player player, Game currentGame) {
        super.execute(player, currentGame);
        marbleMarket = currentGame.getMarbleMarket();
        //player has to pick resources
    }

    private void onMarblesSelected(MarbleSelection selection){
        ArrayList<Marble> selectedMarbles = marbleMarket.sell(selection);
        for(Marble marble : selectedMarbles){
            ResourceType resourceToAdd = marble.getResourceType();
            //player choose location to add resource

        }
    }

    private void onResourceLocationSelected(ResourceType resourceToAdd, int depotIndex){
        currentPlayer.addResourceToDepot(resourceToAdd, depotIndex);
    }
}