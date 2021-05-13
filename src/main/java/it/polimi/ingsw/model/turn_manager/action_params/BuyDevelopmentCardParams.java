package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;

import java.util.Map;

public class BuyDevelopmentCardParams extends  ActionParams{
    final private int xPosition;
    final private int yPosition;
    final private int deckIndex;

    public BuyDevelopmentCardParams(
            int x, int y, int deckI
    ){
        this.xPosition = x;
        this.yPosition = y;
        this.deckIndex = deckI;
    }

    public int getXPosition() { return xPosition; }
    public int getYPosition() { return yPosition; }

    public int getDeckIndex() { return deckIndex; }

}
