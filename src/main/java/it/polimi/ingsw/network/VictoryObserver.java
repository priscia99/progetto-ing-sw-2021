package it.polimi.ingsw.network;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.observer.ObservableWithOption;
import it.polimi.ingsw.network.observer.ObserverWithOption;
import it.polimi.ingsw.network.server.Lobby;

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
    public void update(Object message, MessageType option) {
        // TODO complete with end check
    }
}
