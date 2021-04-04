package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.player_board.LeaderCardsDeck;

import java.util.List;

public class Game {

    private final String id;
    private List<Player> players;
    private int numberOfPlayers;
    private final LeaderCardsDeck leaderCardsDeck;

    public Game(String id, int numberOfPlayers, LeaderCardsDeck leaderCardsDeck) {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
        this.leaderCardsDeck = leaderCardsDeck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getId() {
        return id;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public LeaderCardsDeck getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void nextTurn() {

    }

    public boolean checkVictory() {
        return false;
    }

    public void start() {

    }

    public void setup() {

    }

    private int extractFirstPlayer() {
        return 0;
    }

    private void giveResources() {

    }

    private void setupMarbleMarket() {

    }

    private void setupCardsMarket() {

    }

    private void setupLeaderCards() {

    }

    private void setupPlayerBoards() {

    }

    private void addPlayer(Player player) {

    }
}
