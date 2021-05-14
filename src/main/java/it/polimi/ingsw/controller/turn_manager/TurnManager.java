package it.polimi.ingsw.controller.turn_manager;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.controller.turn_manager.action_params.*;
import it.polimi.ingsw.controller.turn_manager.turn_action.*;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;

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

    public void startTurn() {
        this.mainActionDone = false;
        CustomLogger.getLogger().info(String.format("%s has started the turn.", game.getCurrentPlayer().getNickname()));
    }

    // FIXME move logic in actions and player/game (notify)
    public boolean isActionInvalid(TurnActionType selectedTurnAction){
        return  mainActionDone &&
                (selectedTurnAction == TurnActionType.BUY_DEVELOPMENT_CARD ||
                        selectedTurnAction == TurnActionType.PICK_RESOURCES ||
                        selectedTurnAction == TurnActionType.START_PRODUCTION );
    }

    public void executeBuyDevelopmentCard(BuyDevelopmentCardParams params){
        BuyDevelopmentCardAction action = new BuyDevelopmentCardAction();
        action.execute(game, params);
    }

    public void executeDropLeaderCard(DropLeaderCardParams params){
        DropLeaderCardAction action = new DropLeaderCardAction();
        action.execute(game, params);
    }

    public void executePlayLeaderCard(PlayLeaderCardParams params){
        PlayLeaderCardAction action = new PlayLeaderCardAction();
        action.execute(game, params);
    }

    public void executePickResources(PickResourcesParams params){
        PickResourcesAction action = new PickResourcesAction();
        action.execute(game, params);
    }

    public void executeStartProduction(StartProductionParams params){
        StartProductionAction action = new StartProductionAction();
        action.execute(game, params);
    }

}
