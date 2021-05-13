package it.polimi.ingsw.network;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.network.observer.ObservableWithOption;
import it.polimi.ingsw.network.observer.ObserverWithOption;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;

public class VictoryObserver implements ObserverWithOption<Object, MessageType> {

    private Game game;

    public VictoryObserver(Game game) {
        this.game = game;
    }

    public Game getLobby() {
        return game;
    }

    public void setLobby(Game game) {
        this.game = game;
    }

    @Override
    public void update(Object object, MessageType option) {
        if (option.equals(MessageType.FAITH_PATH_NEXT)) {
            FaithPath faithPath = (FaithPath) object;
            if (faithPath.getFaithPoints() == 20) {
                // trigger last turn
            }
        } else if (option.equals(MessageType.BUY_DEVELOPMENT_CARD)) {
            ArrayList<DevelopmentCard> developmentCards = (ArrayList<DevelopmentCard>) object;
            if (developmentCards.size() == 7) {
                // trigger last turn
            }
        } else {
            throw new IllegalArgumentException("Message type not allowed here");
        }
    }
}
