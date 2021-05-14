package it.polimi.ingsw.controller.turn_manager.action_params;

public class DropLeaderCardParams extends ActionParams {
    final private String cardId;

    public String getCardId(){ return cardId; }

    public DropLeaderCardParams(String cardId){
        this.cardId = cardId;
    }

}
