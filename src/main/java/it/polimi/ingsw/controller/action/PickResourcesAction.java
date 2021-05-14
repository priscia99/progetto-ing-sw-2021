package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.server_model.marble.Marble;
import it.polimi.ingsw.server_model.marble.MarbleSelection;
import it.polimi.ingsw.server_model.resource.ResourceType;

import java.util.ArrayList;

public class PickResourcesAction extends Action {
    private final MarbleSelection marbleSelection;
    private final ArrayList<Integer> positions;

    public PickResourcesAction(MarbleSelection marbleSelection, ArrayList<Integer> positions) {
        this.marbleSelection = marbleSelection;
        this.positions = positions;
    }

    public void execute(Game currentGame) {
        if(currentGame.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        ArrayList<Marble> selectedMarbles = currentGame.getMarbleMarket().sell(marbleSelection);
        for(int i = 0; i<selectedMarbles.size(); i++){
            ResourceType resourceToAdd = selectedMarbles.get(i).getResourceType();
            int selectedPosition = positions.get(i);
            //TODO: -1 corrisponds to dropped resources, should we implement a SelectedPosition enum?
            if(selectedPosition == -1){
                currentGame.getPlayers().stream().filter(player-> player != currentGame.getCurrentPlayer())
                        .forEach(player -> player.addFaithPoints(1));
            } else {
                currentGame.getCurrentPlayer().addResourceToDepot(resourceToAdd, selectedPosition);
            }
        }
        currentGame.getCurrentPlayer().setHasDoneMainAction(true);
    }
}