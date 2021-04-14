package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.turn_manager.TurnManager;
import it.polimi.ingsw.model.turn_manager.turn_action.*;

import java.util.Map;

public class TurnController {
    private TurnManager turnManager;

    public TurnController(TurnManager turnManager){this.turnManager = turnManager;}

    public void playerPickedAction(TurnActionType selectedTurnAction, Map<String, Object> params) throws InvalidActionException {
        if(turnManager.isActionValid(selectedTurnAction)) throw new InvalidActionException();
        switch(selectedTurnAction){
            case BUY_DEVELOPMENT_CARD : turnManager.executeBuyDevelopmentCard(params); break;
            case DROP_LEADER_CARD : turnManager.executeDropLeaderCard(params); break;
            case PLAY_LEADER_CARD: turnManager.executePlayLeaderCard(params); break;
            case PICK_RESOURCES : turnManager.executePickResources(params); break;
            case START_PRODUCTION: turnManager.executeStartProduction(params); break;
            default: throw new InvalidActionException();
        }
    }
}
