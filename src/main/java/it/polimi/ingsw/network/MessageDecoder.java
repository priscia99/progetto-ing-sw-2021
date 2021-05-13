package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.turn_manager.action_params.*;
import it.polimi.ingsw.model.turn_manager.turn_action.TurnActionType;
import it.polimi.ingsw.network.observer.ObserverWithOption;

public class MessageDecoder implements ObserverWithOption<Message, MessageType> {
    private TurnController turnController;

    public MessageDecoder(TurnController controller){
        this.turnController = controller;
    }


    @Override
    public void update(Message message, MessageType type) {
        ActionParams params;
        switch(message.getType()){
            case DROP_LEADER_CARD:
                params = (DropLeaderCardParams) message.getPayload();
                turnController.playerPickedAction(TurnActionType.DROP_LEADER_CARD, params);
                break;
            case PLAY_LEADER_CARD:
                params = (PlayLeaderCardParams) message.getPayload();
                turnController.playerPickedAction(TurnActionType.PLAY_LEADER_CARD, params);
                break;
            case BUY_DEVELOPMENT_CARD:
                params = (BuyDevelopmentCardParams) message.getPayload();
                turnController.playerPickedAction(TurnActionType.BUY_DEVELOPMENT_CARD, params);
                break;
            case PICK_RESOURCES:
                params = (PickResourcesParams) message.getPayload();
                turnController.playerPickedAction(TurnActionType.PICK_RESOURCES, params);
                break;
            case START_PRODUCTION:
                params = (StartProductionParams) message.getPayload();
                turnController.playerPickedAction(TurnActionType.START_PRODUCTION, params);
                break;
        }
    }

}
