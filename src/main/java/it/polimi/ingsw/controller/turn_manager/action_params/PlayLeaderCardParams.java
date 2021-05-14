package it.polimi.ingsw.controller.turn_manager.action_params;

public class PlayLeaderCardParams extends ActionParams{
    final String cardId;
    public PlayLeaderCardParams(String id){
        this.cardId = id;
    }

    public String getCardId(){return cardId;}


}
