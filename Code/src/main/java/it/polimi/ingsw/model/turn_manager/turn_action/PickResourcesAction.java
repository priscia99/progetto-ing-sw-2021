package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.marble.MarbleSelection;
import it.polimi.ingsw.model.marble.Orientation;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.turn_manager.action_params.PickResourcesParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickResourcesAction extends TurnAction{
    private MarbleMarket marbleMarket;

    public PickResourcesAction() {
        this.turnActionType = TurnActionType.PICK_RESOURCES;
    }

    public void execute(Game currentGame, PickResourcesParams params) {
        marbleMarket = currentGame.getMarbleMarket();
        Orientation orientation = params.getOrientation();
        int index = params.getIndex();
        ArrayList<Integer> positionsSelected = params.getPositions();
        MarbleSelection selectionOfUser = new MarbleSelection(orientation, index);
        ArrayList<Marble> selectedMarbles = marbleMarket.sell(selectionOfUser);
        for(int i = 0; i<selectedMarbles.size(); i++){
            ResourceType resourceToAdd = selectedMarbles.get(i).getResourceType();
            int selectedPosition = positionsSelected.get(i);
            //TODO: -1 corrisponds to dropped resources, should we implement a SelectedPosition enum?
            if(selectedPosition == -1){
                currentGame.getPlayers().stream().filter(player-> player != currentGame.getCurrentPlayer())
                        .forEach(player -> player.addFaithPoints(1));
            } else {
                currentGame.getCurrentPlayer().addResourceToDepot(resourceToAdd, selectedPosition);
            }
        }

    }
}