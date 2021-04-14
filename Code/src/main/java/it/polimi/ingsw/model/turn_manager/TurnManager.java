package it.polimi.ingsw.model.turn_manager;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.turn_manager.turn_action.*;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.Map;

public class TurnManager {

    private boolean mainActionDone;
    private final Game game;
    private ArrayList<TurnAction> possibleActions;

    public TurnManager(Game game) {
        this.game = game;
        this.possibleActions = new ArrayList<>();
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        this.mainActionDone = mainActionDone;
    }

    public Game getGame() {
        return game;
    }

    public void startTurn(Player player) {
        this.mainActionDone = false;
        CustomLogger.getLogger().info(String.format("%s has started the turn.", game.getCurrentPlayer().getNickname()));
    }

    public boolean isActionValid(TurnActionType selectedTurnAction){
        return  mainActionDone &&
                (selectedTurnAction == TurnActionType.BUY_DEVELOPMENT_CARD ||
                        selectedTurnAction == TurnActionType.PICK_RESOURCES ||
                        selectedTurnAction == TurnActionType.START_PRODUCTION );
    }

    public void executeBuyDevelopmentCard(Map<String, Object> params){
        BuyDevelopmentCardAction action = new BuyDevelopmentCardAction();
        action.execute(game, params);
    }

    public void executeDropLeaderCard(Map<String, Object> params){
        DropLeaderCardAction action = new DropLeaderCardAction();
        action.execute(game, params);
    }

    public void executePlayLeaderCard(Map<String, Object> params){
        PlayLeaderCardAction action = new PlayLeaderCardAction();
        action.execute(game, params);
    }

    public void executePickResources(Map<String, Object> params){
        PickResourcesAction action = new PickResourcesAction();
        action.execute(game, params);
    }

    public void executeStartProduction(Map<String, Object> params){
        StartProductionAction action = new StartProductionAction();
        action.execute(game, params);
    }

}
