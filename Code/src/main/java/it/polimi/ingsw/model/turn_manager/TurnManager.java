package it.polimi.ingsw.model.turn_manager;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.turn_manager.turn_action.*;
import it.polimi.ingsw.utils.CustomLogger;

public class TurnManager {

    private boolean mainActionDone;
    private Player currentPlayer;
    private final Game game;

    public TurnManager(Game game) {
        this.game = game;
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        this.mainActionDone = mainActionDone;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Game getGame() {
        return game;
    }

    public void startTurn(Player player) {
        this.currentPlayer = player;
        this.mainActionDone = false;
        CustomLogger.getLogger().info(String.format("%s has started the turn.", currentPlayer.getNickname()));
        //ask for action
    }

    //called by controller ( or controller itself )
    public void playerPickedAction(TurnActionType selectedTurnAction) throws InvalidActionException {
        TurnAction turnAction;
        switch(selectedTurnAction){
            case BUY_DEVELOPMENT_CARD : turnAction = new BuyDevelopmentCardAction(); break;
            case DROP_LEADER_CARD : turnAction = new DropLeaderCardAction(); break;
            case PLAY_LEADER_CARD: turnAction = new PlayLeaderCardAction(); break;
            case PICK_RESOURCES : turnAction = new PickResourcesAction(); break;
            case START_PRODUCTION: turnAction = new StartProductionAction(); break;
            default: throw new InvalidActionException();
            }
            turnAction.execute(currentPlayer, game);
    }
}
