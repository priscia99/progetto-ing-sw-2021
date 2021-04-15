package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;
import it.polimi.ingsw.model.marble.Orientation;

import java.util.ArrayList;
import java.util.Map;

public class PickResourcesParams {
    final Orientation orientation;
    final int index;
    final ArrayList<Integer> positions;

    private PickResourcesParams(Orientation orientation, int index, ArrayList<Integer> positions){
        this.orientation = orientation;
        this.index = index;
        this.positions = positions;
    }

    public Orientation getOrientation(){ return orientation; }
    public int getIndex(){ return index ;}
    public ArrayList<Integer> getPositions(){ return positions; }

    static public PickResourcesParams fromMap(Map<String, Object> map){
        try{
            Orientation orientation = (Orientation) map.get("orientation");
            int index = (int) map.get("index");
            ArrayList<Integer> positions = (ArrayList<Integer>) map.get("positions");
            return new PickResourcesParams(orientation, index, positions);
        } catch (Exception e){
            throw new ParamsConvertionException("Error while converting pick resources params");
        }
    }
}
