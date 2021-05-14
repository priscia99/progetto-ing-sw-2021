package it.polimi.ingsw.controller.turn_manager.turn_action;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.marble.Marble;
import it.polimi.ingsw.server_model.marble.MarbleSelection;
import it.polimi.ingsw.server_model.marble.Orientation;
import it.polimi.ingsw.server_model.market.MarbleMarket;
import it.polimi.ingsw.server_model.resource.ResourceType;
import it.polimi.ingsw.controller.turn_manager.action_params.PickResourcesParams;

import java.util.ArrayList;

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