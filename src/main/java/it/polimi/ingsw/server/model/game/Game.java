package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.exceptions.UserNotFoundException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.market.CardsMarket;
import it.polimi.ingsw.server.model.market.MarbleMarket;
import it.polimi.ingsw.server.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Game extends Observable<Message<ClientController>> implements Observer<Message<ClientController>>, Cloneable {

    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private LeaderCardsDeck leaderCardsDeck;
    private CardsMarket cardsMarket;
    private MarbleMarket marbleMarket;
    private boolean isLastRound;

    public Game() {
        CustomLogger.getLogger().info("Creating Game");
        this.isLastRound = false;
        this.currentPlayerIndex = 0;
        CustomLogger.getLogger().info("Game created");
    }

    public Game getBackup(){
        Game backup = new Game();
        ArrayList<Player> playersBackup = new ArrayList<>();
        players.forEach(player->playersBackup.add(player.getCopy()));
        backup.setPlayers(playersBackup);
        backup.setCurrentPlayerIndex(currentPlayerIndex);
        //backup.setLeaderCards(leaderCardsDeck);
        //backup.setCardsMarket(cardsMarket);
        //backup.setMarbleMarket(marbleMarket);
        backup.setIsLastRound(isLastRound);
        return backup;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players  = players;
    }

    private void setCurrentPlayerIndex(int index){
        this.currentPlayerIndex = index;
        notify(new CurrentPlayerMessage(getCurrentPlayer().getNickname()));
    }

    private void setLeaderCards(LeaderCardsDeck cards){
        this.leaderCardsDeck = cards;
    }

    private void setMarbleMarket(MarbleMarket market){
        this.marbleMarket = market;
    }

    private void setCardsMarket(CardsMarket market){
        this.cardsMarket = market;
    }

    private void setIsLastRound(boolean flag){
        this.isLastRound = flag;
    }

    public void applyBackup(Game backup){
        this.setCardsMarket(backup.getCardMarket());
        this.setMarbleMarket(backup.getMarbleMarket());
        this.setLeaderCards(backup.getLeaderCardsDeck());
        this.setPlayers(backup.getPlayers());
        this.setCurrentPlayerIndex(backup.getCurrentPlayerIndex());
        this.setIsLastRound(backup.getIsLastRound());
    }

    public CardsMarket getCardMarket() {
        return cardsMarket;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public MarbleMarket getMarbleMarket(){return marbleMarket;}

    public LeaderCardsDeck getLeaderCardsDeck(){return leaderCardsDeck;}

    public int getCurrentPlayerIndex(){return currentPlayerIndex;}

    public boolean getIsLastRound(){return isLastRound;}


    public void nextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex = currentPlayerIndex % players.size();
        if(isLastRound && getCurrentPlayer().isFirst()){
            finalizeGame();
        } else {
            getCurrentPlayer().setHasDoneMainAction(false);
            notify(new CurrentPlayerMessage(getCurrentPlayer().getNickname()));
        }
    }

    private void finalizeGame(){
        //TODO: for each player calculate and set victory points
    }

    public void setFirstPlayer(){
        Collections.shuffle(this.players);
        players.get(1).setFirst(true);
        notify(new PlayersOrderMessage(players));
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public Player getPlayerByUsername(String username){
        for(Player player : players){
            if(player.getNickname().equals(username)) return player;
        }
        throw new UserNotFoundException();
    }

    public void giveLeaderCardsToPlayers() {
        final int numberCardsToGive = 4;
        for(Player player : players){
            CustomLogger.getLogger().info(String.format("Giving %s initial leader cards", player.getNickname()));
            ArrayList<LeaderCard> cardsToGive = new ArrayList<>();
            for(int i = 0; i<numberCardsToGive; i++){
                cardsToGive.add(this.leaderCardsDeck.pop());
            }
            player.setInitialLeadersToChoose(cardsToGive);
        }
    }

    public void tryStart(){
        if(isReady()) {
            notify(new GameReadyMessage());
            nextTurn();
        }
    }

    private boolean isReady(){
        return allPlayersHasChosenInitialResources() && allPlayersHaveStartingLeaderCards();
    }

    public boolean allPlayersHaveStartingLeaderCards(){
        return !players.stream()
                .map(Player::isInitialLeadersReady)
                .collect(Collectors.toList())
                .contains(false);
    }

    public boolean allPlayersHasChosenInitialResources(){
        return !players.stream()
                .map(Player::isInitialResourcesReady)
                .collect(Collectors.toList())
                .contains(false);
    }

    public void giveInitialResources() {
        System.out.println("Number of players: " + players.size());
        players.get(1).setInitialResourcesReady(true);
        if(players.size()>1){
            players.get(2).setInitialResourceToChoose(1);
            if(players.size()>2){
                players.get(3).setInitialResourceToChoose(1);
                players.get(3).addFaithPoints(1);
                if(players.size()>3){
                    players.get(0).setInitialResourceToChoose(2);
                    players.get(0).addFaithPoints(1);
                }
            }
        }
    }

    public void currentPlayerDropsResource(){
        players.stream().filter(player-> player != getCurrentPlayer())
                .forEach(player -> player.addFaithPoints(1));
    }

    public void setupVictoryObservations(){
        players.forEach((player)->{
            player.getPlayerBoard().getFaithPath().addObserver(this);
            Arrays.asList(player.getPlayerBoard().getDevelopmentCardsDecks()).forEach(
                    deck -> deck.addObserver(this)
            );
        });
    }

    public void setupMarbleMarket() {
        CustomLogger.getLogger().info("Setting up marble market");
        this.marbleMarket = MarbleMarket.getStartingMarket();
    }

    public void setupCardsMarket() {
        CustomLogger.getLogger().info("Setting up cards market");
        this.cardsMarket = CardsMarket.getStartingMarket();
    }

    public void setupLeaderCards() {
        CustomLogger.getLogger().info("Setting up leader cards");
        this.leaderCardsDeck = LeaderCardsDeck.getStartingDeck();
        this.leaderCardsDeck.shuffle();
    }

    @Override
    public void update(Message<ClientController> object) {
        players.forEach((player)->{
            if(player.finishedFaithPath() || player.getTotalDevelopmentCards()>6) {
                isLastRound = true;
                notify(new LastRoundMessage());
            }
        });
    }

    public void notifyError(Exception exception){
        notify(new ExceptionMessage(exception));
    }

}
