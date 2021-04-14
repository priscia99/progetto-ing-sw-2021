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
    private int currentPlayerIndex;
    private LeaderCardsDeck leaderCardsDeck;
    private CardMarket cardMarket;
    private MarbleMarket marbleMarket;
    private TurnManager turnManager;

    public Game(String id) {
        CustomLogger.getLogger().info("Creating Game");
        this.id = id;
        CustomLogger.getLogger().info("Game created");
    }

    public CardMarket getCardMarket() {
        return cardMarket;
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

    public void nextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex = currentPlayerIndex % players.size();
        turnManager.startTurn(players.get(currentPlayerIndex));
        // FIXME
        notify(this);
    }


    public boolean checkVictory() {
        // TODO fill the function
        return false;
    }

    public void setFirstPlayer(){
        Collections.shuffle(this.players);
        players.get(0).setIsFirst(true);
        currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer(){return players.get(currentPlayerIndex);}



    public void start() {
        nextTurn();
        // FIXME
        notify(this);
    }

    public void setup(List<Player> players) {
        this.players = players;
        setupLeaderCards();
        setupCardsMarket();
        setupMarbleMarket();
        setupTurnManager();
        giveLeaderCardsToPlayers();
        giveInitialResources();
    }

    private void giveLeaderCardsToPlayers() {
        final int numberCardsToGive = 4;
        for(Player player : players){
            CustomLogger.getLogger().info(String.format("Giving %s initial leader cards", player.getNickname()));
            List<LeaderCard> cardsToGive = new ArrayList<>();
            for(int i = 0; i<numberCardsToGive; i++){
                cardsToGive.add(this.leaderCardsDeck.pop());
            }
            cardsToGive.forEach(c-> player.getPlayerBoard().getLeaderCardsDeck().addLeader(c));
        }
    }


    public boolean allPlayersHaveStartingLeaderCards(){
        return !players.stream()
                .map(player -> player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size()==2)
                .collect(Collectors.toList())
                .contains(false);
    }

    public boolean allPlayersHasChosedInitialResources(){
        return !players.stream()
                .map(player-> player.hasToChooseInitialResource())
                .collect(Collectors.toList())
                .contains(false);
    }

    public void giveInitialResources() {
        if(players.size()>2){
            players.get(2).setInitialResourceToChoose(1);
            players.get(2).addFaithPoints(1);
            if(players.size()>3){
                players.get(3).setInitialResourceToChoose(2);
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
