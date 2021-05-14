package it.polimi.ingsw.controller.turn_manager.action_params;

import it.polimi.ingsw.server_model.marble.Orientation;

import java.util.ArrayList;

public class PickResourcesParams extends ActionParams{
    final Orientation orientation;
    final int index;
    final ArrayList<Integer> positions;

    public PickResourcesParams(Orientation orientation, int index, ArrayList<Integer> positions){
        this.orientation = orientation;
        this.index = index;
        this.positions = positions;
    }

    public Orientation getOrientation(){ return orientation; }
    public int getIndex(){ return index ;}
    public ArrayList<Integer> getPositions(){ return positions; }

}