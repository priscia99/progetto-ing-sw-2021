package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.turn_manager.TurnManager;
import it.polimi.ingsw.model.turn_manager.action_params.*;
import it.polimi.ingsw.model.turn_manager.turn_action.*;

import java.util.Map;

public class TurnController {
    private TurnManager turnManager;

    public TurnController(TurnManager turnManager){this.turnManager = turnManager;}

    public void playerPickedAction(TurnActionType selectedTurnAction, ActionParams params) throws InvalidActionException {
        if(turnManager.isActionInvalid(selectedTurnAction)) throw new InvalidActionException();
        switch(selectedTurnAction){
            case BUY_DEVELOPMENT_CARD : turnManager.executeBuyDevelopmentCard((BuyDevelopmentCardParams) params); break;
            case DROP_LEADER_CARD : turnManager.executeDropLeaderCard((DropLeaderCardParams) params); break;
            case PLAY_LEADER_CARD: turnManager.executePlayLeaderCard((PlayLeaderCardParams) params); break;
            case PICK_RESOURCES : turnManager.executePickResources((PickResourcesParams) params); break;
            case START_PRODUCTION: turnManager.executeStartProduction((StartProductionParams) params); break;
            default: throw new InvalidActionException();
        }
    }
}
