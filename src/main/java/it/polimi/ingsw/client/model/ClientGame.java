package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.utils.Pair;

import java.util.*;

public class ClientGame extends Observable<Pair<String, Boolean>> {

    private String currentPlayer;

    public boolean isMainActionDone() {
        return isMainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        isMainActionDone = mainActionDone;
    }

    private boolean isMainActionDone;
    private final String myUsername;
    private final ClientCardsMarket clientCardsMarket;

    public ClientCardsMarket getClientCardsMarket() {
        return clientCardsMarket;
    }

    public ClientMarbleMarket getClientMarbleMarket() {
        return clientMarbleMarket;
    }

    private final ClientMarbleMarket clientMarbleMarket;
    private Map<String, ClientPlayerBoard> playerBoardMap = new LinkedHashMap<>();
    private boolean gameStarted = false;
    public ClientGame(String myUsername, String currentPlayer, ArrayList<String> players) {
        this.myUsername = myUsername;
        this.currentPlayer = currentPlayer;
        this.clientCardsMarket = new ClientCardsMarket();
        this.clientMarbleMarket = new ClientMarbleMarket();
        int labelPosition=1;
        for(String playerName : players){

            boolean isMine = playerName.equals(myUsername);
            System.out.println("Device: " + this.myUsername + ", Temp: " + playerName + ", Boolean " + isMine);
            if(isMine)
                this.playerBoardMap.put(playerName, new ClientPlayerBoard(true,0 , playerName));
            else
                this.playerBoardMap.put(playerName, new ClientPlayerBoard(false,labelPosition++, playerName));
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
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
        this.playerBoardMap = playerBoardMap;   // re-assign playerboards map based on order
        getPlayerBoardMap().keySet().forEach(key -> {
            getPlayerBoardMap().get(key).refreshStats();    // refresh player stats for each player
        });
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void displayError(String string){
        notify(new Pair<>(string, false));
    }

}
