package it.polimi.ingsw.client.model;


import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.utils.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ClientGame extends Observable<Pair<String, Boolean>> implements Serializable {

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

    public void setMyUsername(String name){this.myUsername = name;}

    public void setMyProperty(String username){
        this.myUsername = username;
        ClientPlayerBoard myPlayerBoard = this.playerBoardMap.get(username);
        myPlayerBoard.getClientLeaderCards().setMine(true);
        myPlayerBoard.setMine(true);

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
            if(isMine)
                this.playerBoardMap.put(playerName, new ClientPlayerBoard(true,0 , playerName));
            else
                this.playerBoardMap.put(playerName, new ClientPlayerBoard(false,labelPosition++, playerName));
        }
    }
    public ClientGame(String currentPlayer, ArrayList<String> players) {
        this.currentPlayer = currentPlayer;
        this.clientCardsMarket = new ClientCardsMarket();
        this.clientMarbleMarket = new ClientMarbleMarket();
        int labelPosition=1;
        for(String playerName : players){
            this.playerBoardMap.put(playerName, new ClientPlayerBoard(false,labelPosition++, playerName));
        }
    }

    public static  ClientGame fromGame(Game game) throws Exception {
        ClientGame clientGame = new ClientGame(
                game.getCurrentPlayer().getNickname(),
                new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).collect(Collectors.toList())));
        clientGame.setMainActionDone(game.getCurrentPlayer().hasDoneMainAction());
        clientGame.setGameStarted(true);
        clientGame.setClientCardsMarket(new ClientCardsMarket(game.getCardMarket()));
        clientGame.setClientMarbleMarket(new ClientMarbleMarket(
                game.getMarbleMarket().getOnSale(),
                game.getMarbleMarket().getNotForSale()));

        for(String username : clientGame.getPlayerBoardMap().keySet()){
            Player player = game.getPlayerByUsername(username);
            FaithPath path = player.getPlayerBoard().getFaithPath();
            ClientFaithPath faithPath = new ClientFaithPath(path.getFaithPoints(), path.getAcquiredPopeFavours(), username);
            ClientWarehouse warehouse = new ClientWarehouse(player.getPlayerBoard().getWarehouse().getDepots());
            ClientStrongbox strongbox = new ClientStrongbox(player.getPlayerBoard().getStrongbox().getResourceStocks(), username);
            ClientLeaderCardDeck leaders = new ClientLeaderCardDeck(
                   false, username, player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards());

            ClientDevelopmentCardDecks developmentCards = new ClientDevelopmentCardDecks(player.getPlayerBoard().getDevelopmentCardsDecks(), username);
            clientGame.getPlayerBoardMap().put(username,
                    new ClientPlayerBoard(faithPath, warehouse, strongbox, leaders, developmentCards, false));

        }
        return clientGame;
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
