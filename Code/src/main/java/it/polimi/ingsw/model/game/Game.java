package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.market.CardMarket;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.model.turn_manager.TurnManager;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Game {

    private final String id;
    private List<Player> players;
    private ListIterator<Player> playerIterator;
    private LeaderCardsDeck leaderCardsDeck;
    private CardMarket cardMarket;
    private MarbleMarket marbleMarket;
    private TurnManager turnManager;

    public Game(String id, List<Player> players) {
        CustomLogger.getLogger().info("Creating Game");
        this.id = id;
        this.players = players;
        this.setup();
        CustomLogger.getLogger().info("Game created");
        this.start();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getId() {
        return id;
    }

    public LeaderCardsDeck getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    private void nextTurn() {
        if (!playerIterator.hasNext()) {
            playerIterator = players.listIterator();
        }
        turnManager.startTurn(playerIterator.next());
    }

    public boolean checkVictory() {
        return false;
    }

    public void start() {
        Collections.shuffle(this.players);
        players.get(0).setIsFirst(true);
        playerIterator = players.listIterator();
        nextTurn();
    }

    public void setup() {
        setupLeaderCards();
        setupCardsMarket();
        setupMarbleMarket();
        setupTurnManager();
    }

    private int extractFirstPlayer() {
        return 0;
    }

    private void giveResources() {

    }

    private void setupMarbleMarket() {
        CustomLogger.getLogger().info("Setting up marble market");
        this.marbleMarket = MarbleMarket.getStartingMarket();
    }

    private void setupCardsMarket() {
        CustomLogger.getLogger().info("Setting up cards market");
        this.cardMarket = CardMarket.getStartingMarket();
    }

    private void setupLeaderCards() {
        CustomLogger.getLogger().info("Setting up leader cards");
        this.leaderCardsDeck = LeaderCardsDeck.getStartingDeck();
    }

    private void setupTurnManager(){
        CustomLogger.getLogger().info("Setting up turn manager");
        this.turnManager = new TurnManager(this);
    }

    private void setupPlayerBoards() {

    }

    private void addPlayer(Player player) {

    }
}
