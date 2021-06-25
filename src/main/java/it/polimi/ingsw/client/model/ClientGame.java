package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.market.MarbleMarket;
import it.polimi.ingsw.utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ClientGame extends Observable<Pair<String, Boolean>> {

    private String currentPlayer;
    private boolean isMainActionDone;
    private  String myUsername;
    private  ClientCardsMarket clientCardsMarket;
    private  ClientMarbleMarket clientMarbleMarket;
    private boolean gameStarted = false;
    private Map<String, ClientPlayerBoard> playerBoardMap = new LinkedHashMap<>();


    public boolean isMainActionDone() {
        return isMainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        isMainActionDone = mainActionDone;
    }


    public ClientCardsMarket getClientCardsMarket() {
        return clientCardsMarket;
    }

    public ClientMarbleMarket getClientMarbleMarket() {
        return clientMarbleMarket;
    }

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

    public static ClientGame fromGame(Game game){
        ClientGame clientGame = new ClientGame("",
                game.getCurrentPlayer().getNickname(),
                new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).collect(Collectors.toList())));
        clientGame.setMainActionDone(game.getCurrentPlayer().hasDoneMainAction());
        clientGame.setGameStarted(true);
        clientGame.setClientCardsMarket(new ClientCardsMarket(game.getCardMarket()));
        clientGame.setClientMarbleMarket(new ClientMarbleMarket(
                game.getMarbleMarket().getOnSale(),
                game.getMarbleMarket().getNotForSale()));
        return clientGame;
    }

    private void setCardsMarket(){

    }

    private void setClientCardsMarket(ClientCardsMarket market){this.clientCardsMarket = market;}
    private void setClientMarbleMarket(ClientMarbleMarket market){this.clientMarbleMarket = market;}

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


}
