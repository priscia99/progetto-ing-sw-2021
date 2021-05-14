package it.polimi.ingsw.server_model.game;

import it.polimi.ingsw.network.update.UpdateLastRound;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server_model.card.LeaderCard;
import it.polimi.ingsw.server_model.market.CardMarket;
import it.polimi.ingsw.server_model.market.MarbleMarket;
import it.polimi.ingsw.server_model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.network.update.Update;
import it.polimi.ingsw.network.update.UpdateCurrentPlayer;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;
import java.util.stream.Collectors;

public class Game extends Observable<Update> implements Observer<Update> {

    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private LeaderCardsDeck leaderCardsDeck;
    private CardMarket cardMarket;
    private MarbleMarket marbleMarket;
    private boolean isLastRound;

    public Game() {
        CustomLogger.getLogger().info("Creating Game");
        this.isLastRound = false;
        this.currentPlayerIndex = 0;
        CustomLogger.getLogger().info("Game created");
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public MarbleMarket getMarbleMarket(){return marbleMarket;}


    public void nextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex = currentPlayerIndex % players.size();
        if(isLastRound && getCurrentPlayer().isFirst()){
            finalizeGame();
        } else {
            getCurrentPlayer().setHasDoneMainAction(false);
            notify(new UpdateCurrentPlayer(this));
        }
    }

    private void finalizeGame(){
        //TODO: for each player calculate and set victory points
    }

    public void setFirstPlayer(){
        Collections.shuffle(this.players);
        players.get(0).setFirst(true);
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public void setup(ArrayList<Player> players) {
        this.players = players;
        setupVictoryObservations();
        setupLeaderCards();
        setupCardsMarket();
        setupMarbleMarket();
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

    public boolean allPlayersHasChosenInitialResources(){
        return !players.stream()
                .map(Player::hasToChooseInitialResource)
                .collect(Collectors.toList())
                .contains(false);
    }

    // FIXME ????????????????????
    public void giveInitialResources() {
        if(players.size()>2){
            players.get(2).setInitialResourceToChoose(1);
            players.get(2).addFaithPoints(1);
            if(players.size()>3){
                players.get(3).setInitialResourceToChoose(2);
                players.get(3).addFaithPoints(1);
            }
        }
    }

    private void setupVictoryObservations(){
        players.forEach((player)->{
            player.getPlayerBoard().getFaithPath().addObserver(this);
            Arrays.asList(player.getPlayerBoard().getDevelopmentCardsDecks()).forEach(
                    deck -> deck.addObserver(this)
            );
        });
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

    @Override
    public void update(Update object) {
        players.forEach((player)->{
            if(player.finishedFaithPath() || player.getTotalDevelopmentCards()>6) {
                isLastRound = true;
                notify(new UpdateLastRound(this));
                return;
            }
        });
    }
}
