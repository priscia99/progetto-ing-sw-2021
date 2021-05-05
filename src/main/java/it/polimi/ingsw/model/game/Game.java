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

    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private LeaderCardsDeck leaderCardsDeck;
    private CardMarket cardMarket;
    private MarbleMarket marbleMarket;
    private TurnManager turnManager;
    private boolean areWinConditionsMet;

    public Game() {
        CustomLogger.getLogger().info("Creating Game");
        this.areWinConditionsMet = false;
        this.currentPlayerIndex = 0;
        CustomLogger.getLogger().info("Game created");
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public TurnManager getTurnManager() {return turnManager;}

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public MarbleMarket getMarbleMarket(){return marbleMarket;}

    public void nextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex = currentPlayerIndex % players.size();
        turnManager.startTurn();
        // FIXME
        notify(this);
    }


    public void checkVictory() {
        if(players.stream().map(player->player.meetsWinCondition()).collect(Collectors.toList()).size()>0){
            areWinConditionsMet = true;
        }
    }

    public void setFirstPlayer(){
        Collections.shuffle(this.players);
        players.get(0).setIsFirst(true);
    }

    public Player getCurrentPlayer(){return players.get(currentPlayerIndex);}

    public void start() {
        nextTurn();
        // FIXME
        notify(this);
    }

    public void setup(ArrayList<Player> players) {
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
