package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.turn_manager.action_params.*;
import it.polimi.ingsw.model.turn_manager.turn_action.TurnActionType;
import it.polimi.ingsw.network.observer.Observer;
import it.polimi.ingsw.network.observer.ObserverWithOption;

public class MessageDecoder implements Observer<Message> {

    private TurnController turnController;

    public MessageDecoder(TurnController controller){
        this.turnController = controller;
    }


    @Override
    public void update(Message object) {
        ActionParams params;
        switch(object.getType()){
            case DROP_LEADER_CARD:
                params = (DropLeaderCardParams) object.getPayload();
                turnController.playerPickedAction(TurnActionType.DROP_LEADER_CARD, params);
                break;
            case PLAY_LEADER_CARD:
                params = (PlayLeaderCardParams) object.getPayload();
                turnController.playerPickedAction(TurnActionType.PLAY_LEADER_CARD, params);
                break;
            case BUY_DEVELOPMENT_CARD:
                params = (BuyDevelopmentCardParams) object.getPayload();
                turnController.playerPickedAction(TurnActionType.BUY_DEVELOPMENT_CARD, params);
                break;
            case PICK_RESOURCES:
                params = (PickResourcesParams) object.getPayload();
                turnController.playerPickedAction(TurnActionType.PICK_RESOURCES, params);
                break;
            case START_PRODUCTION:
                params = (StartProductionParams) object.getPayload();
                turnController.playerPickedAction(TurnActionType.START_PRODUCTION, params);
                break;
        }
    }

}
