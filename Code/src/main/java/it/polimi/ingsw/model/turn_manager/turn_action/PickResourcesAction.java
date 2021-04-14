package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.marble.MarbleSelection;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Map;

public class PickResourcesAction extends TurnAction{
    private MarbleMarket marbleMarket;

    public PickResourcesAction() {
        this.turnActionType = TurnActionType.PICK_RESOURCES;
    }

    public void execute(Game currentGame, Map<String, String> params) {
        super.execute(currentGame);
        marbleMarket = currentGame.getMarbleMarket();
        //TODO: params{MarbleSelection} che indica la seleziona fatta dall'utente
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