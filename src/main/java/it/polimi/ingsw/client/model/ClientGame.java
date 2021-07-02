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

    private static final long serialVersionUID = 1506L;
    private String currentPlayer;
    private boolean isMainActionDone;
    private  String myUsername;
    private  ClientCardsMarket clientCardsMarket;
    private  ClientMarbleMarket clientMarbleMarket;
    private boolean gameStarted = false;
    private Map<String, ClientPlayerBoard> playerBoardMap = new LinkedHashMap<>();

    /**
     * Initialize the client game
     * @param myUsername player's username
     * @param currentPlayer the name of the current player in turn
     * @param players list of all players
     */
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

    /**
     * Initialize the client game
     * @param currentPlayer the name of the current player in turn
     * @param players list of all players
     */
    public ClientGame(String currentPlayer, ArrayList<String> players) {
        this.currentPlayer = currentPlayer;
        this.clientCardsMarket = new ClientCardsMarket();
        this.clientMarbleMarket = new ClientMarbleMarket();
        int labelPosition=1;
        for(String playerName : players){
            this.playerBoardMap.put(playerName, new ClientPlayerBoard(false,labelPosition++, playerName));
        }
    }

    /**
     * Converts a game from the server model to one compatible with the client model
     * @param game game from server model
     * @return game compatible with the client model
     * @throws Exception generic exception
     */
    public static ClientGame fromGame(Game game) throws Exception {
        ClientGame clientGame = new ClientGame(
                game.getCurrentPlayer().getNickname(),
                new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).collect(Collectors.toList())));
        clientGame.setMainActionDone(game.getCurrentPlayer().hasDoneMainAction());
        clientGame.setGameStarted(true);
        clientGame.setClientCardsMarket(new ClientCardsMarket(game.getCardMarket()));
        clientGame.setClientMarbleMarket(new ClientMarbleMarket(
                game.getMarbleMarket().getOnSale(),
                game.getMarbleMarket().getNotForSale()));
        int order=0;
        for(String username : clientGame.getPlayerBoardMap().keySet()){
            Player player = game.getPlayerByUsername(username);
            int victoryPoints = player.getVictoryPoints();
            FaithPath path = player.getPlayerBoard().getFaithPath();
            ClientFaithPath faithPath = new ClientFaithPath(path.getFaithPoints(), path.getAcquiredPopeFavours(), username);
            ClientWarehouse warehouse = new ClientWarehouse(player.getPlayerBoard().getWarehouse().getDepots(), username);
            ClientStrongbox strongbox = new ClientStrongbox(player.getPlayerBoard().getStrongbox().getResourceStocks(), username);
            ClientLeaderCardDeck leaders = new ClientLeaderCardDeck(
                    false, username, player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards());

            ClientDevelopmentCardDecks developmentCards = new ClientDevelopmentCardDecks(player.getPlayerBoard().getDevelopmentCardsDecks(), username);
            clientGame.getPlayerBoardMap().put(username,
                    new ClientPlayerBoard(faithPath, warehouse, strongbox, leaders, developmentCards, false, victoryPoints, username, order));
            order++;
        }
        return clientGame;
    }

    /**
     * Sets all game playerboards
     * @param playerBoardMap map with player name as key and playerboard as value
     */
    public void setPlayerBoardMap(Map<String, ClientPlayerBoard> playerBoardMap) {
        this.playerBoardMap = playerBoardMap;   // re-assign playerboards map based on order
        getPlayerBoardMap().keySet().forEach(key -> {
            getPlayerBoardMap().get(key).refreshStats();    // refresh player stats for each player
        });
    }

    /**
     * Set isMine attribute for objects owned by player
     * @param username
     */
    public void setMyProperty(String username){
        this.myUsername = username;
        ClientPlayerBoard myPlayerBoard = this.playerBoardMap.get(username);
        myPlayerBoard.getClientLeaderCards().setMine(true);
        myPlayerBoard.setMine(true);

    }

    public boolean isMainActionDone() {
        return isMainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        isMainActionDone = mainActionDone;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String name){this.myUsername = name;}

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
        notify(new Pair<>(this.currentPlayer, this.currentPlayer.equals(this.myUsername)));
    }

    public ClientCardsMarket getClientCardsMarket() {
        return clientCardsMarket;
    }

    public ClientMarbleMarket getClientMarbleMarket() {
        return clientMarbleMarket;
    }

    private void setClientCardsMarket(ClientCardsMarket market){this.clientCardsMarket = market;}

    private void setClientMarbleMarket(ClientMarbleMarket market){this.clientMarbleMarket = market;}

    public Map<String, ClientPlayerBoard> getPlayerBoardMap() {
        return playerBoardMap;
    }

}
