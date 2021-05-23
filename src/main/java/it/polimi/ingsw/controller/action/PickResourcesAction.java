package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.marble.Marble;
import it.polimi.ingsw.model.marble.MarbleSelection;
import it.polimi.ingsw.model.resource.ResourcePosition;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class PickResourcesAction extends Action {
    private final MarbleSelection marbleSelection;
    private final ArrayList<ResourcePosition> positions;

    public PickResourcesAction(MarbleSelection marbleSelection, ArrayList<ResourcePosition> positions) {
        this.marbleSelection = marbleSelection;
        this.positions = positions;
    }

    public void execute(Game currentGame) {
        if(currentGame.getCurrentPlayer().hasDoneMainAction()) throw new InvalidActionException();
        ArrayList<Marble> selectedMarbles = currentGame.getMarbleMarket().sell(marbleSelection);
        for(int i = 0; i<selectedMarbles.size(); i++){
            ResourceType resourceToAdd = selectedMarbles.get(i).getResourceType();
            ResourcePosition selectedPosition = positions.get(i);
            if(selectedPosition == ResourcePosition.DROPPED){
                currentGame.currentPlayerDropsResource();
            } else {
                currentGame.getCurrentPlayer().addResourceToDepot(resourceToAdd, selectedPosition.ordinal());
            }
        }
        currentGame.getCurrentPlayer().setHasDoneMainAction(true);
    }
}