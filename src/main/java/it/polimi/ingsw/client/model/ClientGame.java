package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Pair;

import java.util.*;

// TODO consider changing Observable<String> (split observable or concentrate observable)
public class ClientGame extends Observable<Pair<String, Boolean>> {

    private String currentPlayer;
    private String myUsername;
    private ClientCardsMarket clientCardsMarket;
    private ClientMarbleMarket clientMarbleMarket;
    private Map<String, ClientPlayerBoard> playerBoardMap = new HashMap<>();

    public ClientGame(String myUsername, String currentPlayer, ArrayList<String> players) {
        this.myUsername = myUsername;
        this.currentPlayer = currentPlayer;
        this.clientCardsMarket = new ClientCardsMarket();
        this.clientMarbleMarket = new ClientMarbleMarket();
        players.forEach(player -> {
            this.playerBoardMap.put(player, new ClientPlayerBoard());
        });
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;

        notify(new Pair<String, Boolean>(this.currentPlayer, this.currentPlayer.equals(this.myUsername)));
    }

    public Map<String, ClientPlayerBoard> getPlayerBoardMap() {
        return playerBoardMap;
    }

    public void setPlayerBoardMap(Map<String, ClientPlayerBoard> playerBoardMap) {
        this.playerBoardMap = playerBoardMap;
    }

    public String getMyUsername() {
        return myUsername;
    }
}
