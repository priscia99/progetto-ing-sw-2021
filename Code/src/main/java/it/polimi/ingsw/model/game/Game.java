package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.market.CardMarket;
import it.polimi.ingsw.model.market.MarbleMarket;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.model.turn_manager.TurnManager;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;
import java.util.stream.Collectors;

public class Game extends Observable<Game> {

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
        CustomLogger.getLogger().info("Waiting for all players to choose leader cards");
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

    public MarbleMarket getMarbleMarket(){return marbleMarket;}

    private void nextTurn() {
        if (!playerIterator.hasNext()) {
            playerIterator = players.listIterator();
        }
        turnManager.startTurn(playerIterator.next());

        // FIXME
        notify(this);
    }

    public boolean checkVictory() {
        // TODO fill the function
        return false;
    }

    private void start() {
        Collections.shuffle(this.players);
        players.get(0).setIsFirst(true);
        giveInitialResources();
        playerIterator = players.listIterator();
        nextTurn();

        // FIXME
        notify(this);
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
            CustomLogger.getLogger().info(String.format("Giving %s initial leader cards", player.getNickname()));
            List<LeaderCard> cardsToGive = new ArrayList<>();
            for(int i = 0; i<numberCardsToGive; i++){
                cardsToGive.add(this.leaderCardsDeck.pop());
            }
            player.receiveCardsToChoose(cardsToGive);
        }
    }

    //will be called by controller ( probably moved into controller )
    public void playerHasChosenLeaderCards(String playerId, List<LeaderCard> leaderCards){
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
        //players.get(1).askForResourceType();
        //listenForResponses();

        if(players.size()>2){
            //players.get(2).askForResorceType();
            players.get(2).addFaithPoints(1);
            if(players.size()>3){
                //players.get(3).askForResorceType();
                //players.get(3).askForResorceType();
                players.get(3).addFaithPoints(1);
            }
        }

        // FIXME
        notify(this);
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
