package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;

import java.util.Map;

public class BuyDevelopmentCardParams {
    final private int xPosition;
    final private int yPosition;
    final private int deckIndex;

    private BuyDevelopmentCardParams(
            int x, int y, int deckI
    ){
        this.xPosition = x;
        this.yPosition = y;
        this.deckIndex = deckI;
    }

    public int getXPosition() { return xPosition; }
    public int getYPosition() { return yPosition; }

    public int getDeckIndex() { return deckIndex; }

    static public BuyDevelopmentCardParams fromMap(Map<String, Object> map){
        try{
            int xPosition = (int) map.get("xPosition");
            int yPosition = (int) map.get("yPosition");
            int deckIndex = (int) map.get("deckIndex");
            return new BuyDevelopmentCardParams(xPosition, yPosition, deckIndex);
        } catch (Exception e){
            throw new ParamsConvertionException("Error converting to BuyDevelopmentCardParams");
        }
    }
}
