package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.market.CardsMarket;
import it.polimi.ingsw.server.model.market.MarbleMarket;
import it.polimi.ingsw.server.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;
import java.util.stream.Collectors;

public class Game extends Observable<Message<ClientController>> implements Observer<Object> {

    private ArrayList<Player> players;
    protected int currentPlayerIndex;
    protected LeaderCardsDeck leaderCardsDeck;
    protected CardsMarket cardsMarket;
    protected MarbleMarket marbleMarket;
    protected boolean isLastRound;
    private boolean started = false;
    private ArrayList<Player> disconnecteds;

    public Game() {
        CustomLogger.getLogger().info("Creating Game");
        this.isLastRound = false;
        this.currentPlayerIndex = -1;
        this.disconnecteds = new ArrayList<>();
        CustomLogger.getLogger().info("Game created");
    }

    /**
     * Setups structures of the game
     * @param players Players that are playing in this game
     * @throws Exception
     */
    public void setup(ArrayList<Player> players) throws Exception {
        this.setPlayers(players);
        this.setupVictoryObservations();
        this.setupLeaderCards();
        this.setupCardsMarket();
        this.setupMarbleMarket();
    }

    /**
     * Create a copy of the game to use as backup
     * @return the backup of this game
     * @throws Exception
     */
    public Game getBackup() throws Exception {
        Game backup = new Game();
        ArrayList<Player> playersBackup = new ArrayList<>();
        for(Player player : players){
            playersBackup.add(player.getCopy());
        }
        backup.setPlayers(playersBackup);
        backup.setCurrentPlayerIndex(currentPlayerIndex);
        backup.setLeaderCards(leaderCardsDeck.getCopy());
        backup.setCardsMarket(cardsMarket.getCopy());
        backup.setMarbleMarket(marbleMarket.getCopy());
        backup.setIsLastRound(isLastRound);
        return backup;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players  = players;
    }

    protected void setCurrentPlayerIndex(int index){
        this.currentPlayerIndex = index;
        notify(new CurrentPlayerMessage(getCurrentPlayer().getNickname()));
    }

    protected void setLeaderCards(LeaderCardsDeck cards){
        this.leaderCardsDeck = cards;
    }

    protected void setMarbleMarket(MarbleMarket market){
        this.marbleMarket = market;
    }

    protected void setCardsMarket(CardsMarket market){
        this.cardsMarket = market;
    }

    protected void setIsLastRound(boolean flag){
        this.isLastRound = flag;
    }

    public CardsMarket getCardMarket() {
        return cardsMarket;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public MarbleMarket getMarbleMarket(){return marbleMarket;}

    public LeaderCardsDeck getLeaderCardsDeck(){return leaderCardsDeck;}


    /**
     * Change current player and check is game has to end
     * @throws Exception
     */
    public void nextTurn() throws Exception {
        currentPlayerIndex++;
        currentPlayerIndex = currentPlayerIndex % players.size();
        if(disconnecteds.contains(getCurrentPlayer())) {
            nextTurn();
            return;
        }
        if(isLastRound && getCurrentPlayer().isFirst()){
            finalizeGame();
        } else {
            getCurrentPlayer().setHasDoneMainAction(false);
            notify(new CurrentPlayerMessage(getCurrentPlayer().getNickname()));
        }
    }

    /**
     * Calculate all victory points and choose the winner
     * @throws Exception
     */
    private void finalizeGame() throws Exception {
        for (Player player : this.players) {
            notify(new VictoryPointsMessage(player.getVictoryPoints(), player.getNickname()));
        }
        notify(new WinnerMessage(this.getWinnersUsername()));
    }

    /**
     * Choose the winner
     * @return The names of winner players ( multiple in case of draw )
     * @throws Exception
     */
    public ArrayList<String> getWinnersUsername() throws Exception{
        ArrayList<Player> sorted = this.players.stream().sorted(Comparator.comparing(Player::getVictoryPoints)).collect(Collectors.toCollection(ArrayList::new));
        int maxVictoryPoints = sorted.get(sorted.size()-1).getVictoryPoints();
        sorted = sorted.stream().filter(player->player.getVictoryPoints() == maxVictoryPoints).collect(Collectors.toCollection(ArrayList::new));
        OptionalInt maxResourcesResult = sorted.stream().mapToInt(Player::getResourceCount).max();
        if(maxResourcesResult.isEmpty()) throw new Exception("No players found to assign victory!");
        int maxResources = maxResourcesResult.getAsInt();
        sorted = sorted.stream().filter(player->player.getResourceCount() == maxResources).collect(Collectors.toCollection(ArrayList::new));
        return sorted.stream().map(Player::getNickname).collect(Collectors.toCollection(ArrayList::new));
    }

    public void setFirstPlayer(){
        Collections.shuffle(this.players);
        players.get(0).setFirst(true);
        notify(new PlayersOrderMessage(players));
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public Player getPlayerByUsername(String username) throws Exception {
        for(Player player : players){
            if(player.getNickname().equals(username)) return player;
        }
        throw new Exception("No player found with that username: " + username);
    }

    /**
     * Assign initial leader cards to player, from which they will have to choose
     * @throws Exception
     */
    public void giveLeaderCardsToPlayers() throws Exception {
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

    /**
     * If all players has chosen initial assets, start the game
     * @throws Exception
     */
    public void tryStart() throws Exception {
        if(isReady()) {
            notify(new GameReadyMessage());
            notify(new CardsMarketMessage(getCardMarket()));
            notify(new MarbleMarketMessage(getMarbleMarket()));
            nextTurn();
            started = true;
        }
    }

    /**
     *
     * @return Check if all players have chosen initial assets
     */
    private boolean isReady(){
        return allPlayersHasChosenInitialResources() && allPlayersHaveStartingLeaderCards();
    }

    public boolean isStarted(){return started;}

    /**
     *
     * @return check if all players have chosen initial leader cards
     */
    public boolean allPlayersHaveStartingLeaderCards(){
        return !players.stream()
                .map(Player::isInitialLeadersReady)
                .collect(Collectors.toList())
                .contains(false);
    }

    /**
     *
     * @return check if all players has chosen initial resources
     */
    public boolean allPlayersHasChosenInitialResources(){
        return !players.stream()
                .map(Player::isInitialResourcesReady)
                .collect(Collectors.toList())
                .contains(false);
    }

    /**
     * Assign initial resources when game is starting
     */
    public void giveInitialResources() {
        players.get(0).setInitialResourcesReady(true);
        if(players.size()>1){
            players.get(1).setInitialResourceToChoose(1);
            if(players.size()>2){
                players.get(2).setInitialResourceToChoose(1);
                players.get(2).addFaithPoint();
                if(players.size()>3){
                    players.get(3).setInitialResourceToChoose(2);
                    players.get(3).addFaithPoint();
                }
            }
        }
    }

    /**
     * Adds faith points to player and check pope favour activation
     * @param target Player targeted
     * @param points Points to add
     */
    public void addFaithPointsToPlayer(Player target, int points){
        for(int i = 0; i<points; i++){
            int position = target.addFaithPoint();
            for(Player player : players){
                player.checkPopeFavour(position);
            }
        }
        notify(new UpdateVictoryPointsMessage(target.getVictoryPoints(), target.getNickname()));
    }

    /**
     * Add faith points to all players excepts for the current one
     */
    public void currentPlayerDropsResource(){
        players.stream().filter(player-> player != getCurrentPlayer())
                .forEach(player -> addFaithPointsToPlayer(player, 1));
    }

    /**
     * Setup observation to development cards and faith path of players
     * to check victory conditions
     */
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

    public void setupCardsMarket() throws Exception {
        CustomLogger.getLogger().info("Setting up cards market");
        this.cardsMarket = CardsMarket.getStartingMarket();
    }

    public void setupLeaderCards() {
        CustomLogger.getLogger().info("Setting up leader cards");
        this.leaderCardsDeck = LeaderCardsDeck.getStartingDeck();
        this.leaderCardsDeck.shuffle();
    }

    /**
     * Check victory condition when development card or faith path of players changes
     * @param object Can be DevelopmentCardsDeck or FaithPath
     */
    @Override
    public void update(Object object) {
        players.forEach((player)->{
            if(player.hasFinishedFaithPath() || player.getTotalDevelopmentCards()>6) {
                isLastRound = true;
                notify(new LastRoundMessage());
            }
        });
    }

    /**
     * Create error message to send to players
     * @param errorMessage Error to show
     * @param player Player that has triggered the error
     */
    public void notifyError(String errorMessage, String player){
        notify(new ExceptionMessage(errorMessage, player));
    }

    /**
     * Add a disconnected player to list of disconnected
     * @param disconnected Name of player disconnected
     * @throws Exception
     */
    public void addDisconnected(String disconnected) throws Exception {
        this.disconnecteds.add(getPlayerByUsername(disconnected));
    }

    /**
     * Remove a disconnected player from list of disconnected
     * @param connected Name of player reconnected
     * @throws Exception
     */
    public void removeDisconnected(String connected) throws Exception {
        this.disconnecteds.remove(getPlayerByUsername(connected));
    }

    /**
     *
     * @return Number of players disconnected
     */
    public int countDisconnected(){
        return this.disconnecteds.size();
    }
}
