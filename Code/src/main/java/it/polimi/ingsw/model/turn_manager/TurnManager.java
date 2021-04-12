package it.polimi.ingsw.model.turn_manager;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.turn_manager.turn_action.*;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;

public class TurnManager {

    private boolean mainActionDone;
    private Player currentPlayer;
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
        //notify per aggiornare il client, questo una volta ricevuto stampa "E' il tuo turno"|"E' il turno di X"
    }

    private boolean checkActionIsInvalid(TurnActionType selectedTurnAction){
        //TODO: calculate possibleActions
        //TODO: check action is in possibleActions
        return  mainActionDone &&
                (selectedTurnAction == TurnActionType.BUY_DEVELOPMENT_CARD ||
                        selectedTurnAction == TurnActionType.PICK_RESOURCES ||
                        selectedTurnAction == TurnActionType.START_PRODUCTION );
    }

    //called by controller ( or controller itself )
    public void playerPickedAction(TurnActionType selectedTurnAction) throws InvalidActionException {
        TurnAction turnAction;
        if(checkActionIsInvalid(selectedTurnAction)) throw new InvalidActionException();
        switch(selectedTurnAction){
            //TODO: params:{x,y} che indica la posizione della carta comprata
            case BUY_DEVELOPMENT_CARD : turnAction = new BuyDevelopmentCardAction(); break;
            //TODO: params:{cardId} che indica la carta che deve scartare
            case DROP_LEADER_CARD : turnAction = new DropLeaderCardAction(); break;
            //TODO: params{cardId} che indica la carta che deve attivate
            case PLAY_LEADER_CARD: turnAction = new PlayLeaderCardAction(); break;
            //TODO: params{MarbleSelection} che indica la seleziona fatta dall'utente
            case PICK_RESOURCES : turnAction = new PickResourcesAction(); break;
            //TODO: params{newWareHouse, newStrongBox, productionsToActivate}
            case START_PRODUCTION: turnAction = new StartProductionAction(); break;
            default: throw new InvalidActionException();
            }
            //notify interne, già all'interno mandano il messaggio di cosa è cambiato e come
            turnAction.execute(currentPlayer, game);
    }
}
