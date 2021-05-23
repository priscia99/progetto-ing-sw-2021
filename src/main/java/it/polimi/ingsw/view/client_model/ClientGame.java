package it.polimi.ingsw.view.client_model;

import it.polimi.ingsw.observer.Observable;

import java.util.HashMap;
import java.util.Map;

// TODO consider changing Observable<String> (split observable or concentrate observable)
public class ClientGame extends Observable<String> {

    private String currentPlayer;
    private ClientCardsMarket clientCardsMarket;
    private ClientMarbleMarket clientMarbleMarket;
    private Map<String, ClientPlayerBoard> playerBoardMap = new HashMap<>();

    public ClientGame(String currentPlayer, Map<String, ClientPlayerBoard> playerBoardMap) {
        this.currentPlayer = currentPlayer;
        this.playerBoardMap = playerBoardMap;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;

        notify(currentPlayer);
    }

    public Map<String, ClientPlayerBoard> getPlayerBoardMap() {
        return playerBoardMap;
    }

    public void setPlayerBoardMap(Map<String, ClientPlayerBoard> playerBoardMap) {
        this.playerBoardMap = playerBoardMap;
    }
}
