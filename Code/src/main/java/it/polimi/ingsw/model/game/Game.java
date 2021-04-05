package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.market.CardMarket;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.model.turn_manager.TurnManager;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;
import java.util.stream.Collectors;

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
        giveInitialResources();
        playerIterator = players.listIterator();
        nextTurn();
    }

    public void setup() {
        setupLeaderCards();
        setupCardsMarket();
        setupMarbleMarket();
        setupTurnManager();
        giveLeaderCardsToPlayers();
    }

    private void giveLeaderCardsToPlayers() {
        final int numberCardsToGive = 4;
        for(Player player : players){
            List<LeaderCard> cardsToGive = new ArrayList<>();
            for(int i = 0; i<numberCardsToGive; i++){
                cardsToGive.add(this.leaderCardsDeck.pop());
            }
            player.receiveCardsToChoose(cardsToGive);
        }
    }

    public void playerHasChoosenLeaderCards(String playerId, List<LeaderCard> leaderCards){
        players.get(players.indexOf(playerId)).pickedLeaderCards(leaderCards);
        if(allPlayersHaveLeaderCards()) start();
    }


    private boolean allPlayersHaveLeaderCards(){
        return !players.stream()
                .map(Player::hasLeaderCards)
                .collect(Collectors.toList())
                .contains(false);
    }

    private void giveInitialResources() {

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
        this.leaderCardsDeck.shuffle();
    }

    private void setupTurnManager(){
        CustomLogger.getLogger().info("Setting up turn manager");
        this.turnManager = new TurnManager(this);
    }
}
