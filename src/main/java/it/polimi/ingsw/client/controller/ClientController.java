package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_client.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.effect.*;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientController extends Observable<Message<ServerController>> {

    private ClientGame game;
    UI userInterface;

    /**
     * Initialize the client controller
     * @param game client game
     * @param userInterface client instance user interface
     */
    public ClientController(ClientGame game, UI userInterface) {
        this.game = game;
        this.userInterface = userInterface;
    }

    /**
     * Set the current player in turn
     * @param player username of the player in turn
     */
    public void setCurrentPlayer(String player){
        game.setCurrentPlayer(player);
    }

    /**
     * Flag the main action as done for the player in turn
     * @param done set true is action is done, false otherwise
     */
    public void setMainActionDone(boolean done){
        game.setMainActionDone(done);
    }

    /**
     * Retrieves the client game
     * @return client game
     */
    public ClientGame getGame() {
        return game;
    }

    /**
     * Set the current game
     * @param game current client game
     */
    public void setGame(ClientGame game){
        this.game = game;
    }

    /**
     * Apply the game backup
     * @param backup client game backup
     */
    public void applyGameBackup(ClientGame backup){
        this.game = backup;
    }

    /**
     * Tells the user interface to choose initial resources
     * @param toChoose number of resources to choose
     * @param username name of the username who has to choose resources
     */
    public void viewChooseInitialResources(int toChoose, String username){
        if(username.equals(game.getMyUsername())) {
            userInterface.displayChooseInitialResourcesMenu(toChoose);
        }
    }

    /**
     * Tells the user interface to choose initial leader cards
     * @param username name of the username who has to choose leader cards
     */
    public void viewChooseInitialLeaders(String username){
        if(username.equals(game.getMyUsername())) {
            userInterface.displayInitialLeadersMenu(
                    game.getPlayerBoardMap()
                            .get(username)
                            .getClientLeaderCards()
                            .getClientLeaderCards()
                    .stream()
                    .map(ClientCard::getId)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    /**
     * Notify the message encoder to send a choose initial resources message
     * @param resources chosen resources
     */
    public void chooseInitialResources(ConsumeTarget resources){
            notify(new ChosenInitialResourcesMessage(resources, game.getMyUsername()));
    }

    /**
     * Notify the message encoder to send a choose initial leaders message
     * @param selected IDs of chosen leader cards
     */
    public void chooseInitialLeaders(ArrayList<String> selected){
            notify(new ChosenInitialLeadersMessage(selected, game.getMyUsername()));
    }

    /**
     * Tells the user interface to refresh leader cards for a targeted player
     * @param cards list of client leader cards
     * @param player name of leader cards' owner
     */
    public void refreshLeaderCards(ArrayList<ClientLeaderCard> cards, String player) {
        boolean displayToView = getGame().isGameStarted() || player.equals(getGame().getMyUsername());
        if(displayToView) {
            if(player.equals(getGame().getMyUsername()))
                System.out.println("Your leader cards have changed!");
            else
                System.out.println(player + "'s leader cards have changed" );
        }
        this.getGame().getPlayerBoardMap().get(player).getClientLeaderCards().setClientLeaderCards(cards, displayToView);
    }

    /**
     * Tells the user interface to refresh the faith path for a targeted player
     * @param faithPoints number of faith points
     * @param popeFavors list of pope favors
     * @param player name of the faith path's owner
     */
    public void refreshFaithPath(int faithPoints, ArrayList<Boolean> popeFavors, String player){
        boolean displayToView = getGame().isGameStarted() || player.equals(getGame().getMyUsername());
        if(displayToView){
            if(player.equals(getGame().getMyUsername()))
                System.out.println("Your faith path is changed!");
            else
                System.out.println(player + "'s faith path is changed" );
        }
        ClientFaithPath faithPath = this.getGame().getPlayerBoardMap().get(player).getFaithPath();
        faithPath.setPopeFavors(popeFavors);
        faithPath.setFaithPoints(faithPoints);
        faithPath.show(displayToView);
    }

    /**
     * Tells the user interface to refresh the faith path in single player game
     * @param popeFavors list of pope favors
     * @param blackCrossPosition name of the faith path's owner
     */
    public void refreshFaithPath(ArrayList<Boolean> popeFavors, int blackCrossPosition){
        ClientFaithPath faithPath = this.getGame().getPlayerBoardMap().get(game.getCurrentPlayer()).getFaithPath();
        faithPath.setPopeFavors(popeFavors);
        faithPath.setBlackCrossPosition(blackCrossPosition);
        faithPath.show(true);
    }

    /**
     * Tells the user interface to refresh the warehouse
     * @param resourceDepots list of resource depots
     * @param player name of the warehouse's owner
     */
    public void refreshWarehouse(ArrayList<ResourceDepot> resourceDepots, String player){
        this.getGame().getPlayerBoardMap().get(player).getWarehouse().setResourceDepots(resourceDepots);
    }

    /**
     * Tells the user interface to refresh the development cards market
     * @param decks development cards market
     */
    public void refreshCardMarket(ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks){
        this.getGame().getClientCardsMarket().setDecks(decks);
    }

    /**
     * Tells the user interface to refresh the strongbox
     * @param resourceStocks list of resources stocks
     * @param player name of the strongbox's owner
     */
    public void refreshStrongbox(ArrayList<ResourceStock> resourceStocks, String player){
        this.getGame().getPlayerBoardMap().get(player).getStrongbox().setResourceStocks(resourceStocks);
    }

    /**
     * Tells the CLI to start listening commands from player
     */
    public void startListening(){
        this.getGame().setGameStarted(true);
        userInterface.startListening();
    }

    /**
     * Tells the user interface to show the leader cards
     * @param player name of the player whose leader cards has to be shown
     */
    public void viewLeaderCards(String player){
        game.getPlayerBoardMap().get(player).getClientLeaderCards().show();
    }

    /**
     * Tells the user interface to show the development cards
     * @param player name of the player whose development cards has to be shown
     */
    public void viewDevelopmentCards(String player){
        game.getPlayerBoardMap().get(player).getDevelopmentCards().show();
    }

    /**
     * Tells the user interface to show the warehouse
     * @param player name of the player whose warehouse has to be shown
     */
    public void viewWarehouse(String player){
        game.getPlayerBoardMap().get(player).getWarehouse().show();
    }

    /**
     * Tells the user interface to show the strongbox
     * @param player name of the player whose strongbox has to be shown
     */
    public void viewStrongbox(String player){
        game.getPlayerBoardMap().get(player).getStrongbox().show();
    }

    /**
     * Tells the user interface to show the faith path
     * @param player name of the player whose faith path has to be shown
     */
    public void viewFaithPath(String player){
        game.getPlayerBoardMap().get(player).getFaithPath().show();
    }

    /**
     * Tells the CLI to show the list of other players name
     */
    public void viewOtherPlayersUsername(){
        ArrayList<String> usernames = new ArrayList<>(game.getPlayerBoardMap().keySet());
        userInterface.displayOtherPlayersUsername(usernames);
    }

    /**
     * Tells the CLI to show the command help message
     */
    public void viewHelpMessage(){
        userInterface.displayHelpMessage();
    }

    /**
     * Tells the CLI to show the turn information
     */
    public void viewTurnInfo(){
        userInterface.displayTurnInfo(new ArrayList<>(game.getPlayerBoardMap().keySet()), game.getCurrentPlayer());
    }

    /**
     * Tells the user interface to show the marble market
     */
    public void viewMarbleMarket(){
        userInterface.displayMarbleMarket(game.getClientMarbleMarket());
    }

    /**
     * Tells the user interface to show the card market
     */
    public void viewCardMarket(){
        userInterface.displayCardMarket(game.getClientCardsMarket());
    }

    /**
     * Tells the CLI to show all possible actions
     */
    public void viewPossibleActions(){
        boolean isMyTurn = game.getCurrentPlayer().equals(game.getMyUsername());
        userInterface.displayPossibleActions(isMyTurn, game.isMainActionDone());
    }

    /**
     * Tells the user interface to display an error message
     * @param message error message
     */
    public void viewErrorMessage(String message){
        userInterface.displayError(message);
    }

    /**
     * Tells the user interface to display the info message
     * @param message information message
     */
    public void viewInfoMessage(String message){userInterface.displayInfo(message);}

    /**
     * Excetute a player's action if it is in turn, otherwise displays an error
     * @param action action to be executed
     */
    private void executeIfCurrentPlayer(Runnable action){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            action.run();
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    /**
     * Execute a player's main action if it has not done yet, otherwise displays an error
     * @param action action to be executed
     */
    private void executeIfNotMainActionYet(Runnable action){
        if(game.isMainActionDone()){
            userInterface.displayError("You have already done main action!");
        } else {
            action.run();
        }
    }

    /**
     * Notify the message encoder to activate a leader card
     * @param cardId id of the leader card that has to be activated
     */
    public void activateLeaderCard(String cardId){
        executeIfCurrentPlayer(()->notify(new PlayLeaderCardMessage(cardId)));
    }

    /**
     * Notify the message encorder to drop a leader card
     * @param cardId id of the leader card that has to be dropped
     */
    public void dropLeaderCard(String cardId){
        executeIfCurrentPlayer(()->notify(new DropLeaderCardMessage(cardId)));
    }

    /**
     * Notify the message encoder that the user has ended its turn
     */
    public void endTurn(){
        executeIfCurrentPlayer(()->notify(new EndTurnMessage()));
    }

    /**
     * Notify the message encoder that the player wants to swap two depots
     * @param first first depot that needs to be swapped
     * @param second second depot that needs to be swapped
     */
    public void swapDepots(int first, int second){
        executeIfCurrentPlayer(()->notify(new SwapDepotsMessage(first, second)));
    }

    /**
     * Notify the message encoder that the player wants to buy a development card
     * @param cardId id of the development card that player wants to buy
     * @param deckIndex deck index in which the player wants to put the development card
     * @param toConsume resources used for this buy action
     */
    public void buyDevelopmentCard(String cardId, int deckIndex,  ConsumeTarget toConsume){
        executeIfCurrentPlayer(
                ()->executeIfNotMainActionYet(
                        ()->notify(new BuyDevelopmentCardMessage(cardId, deckIndex,toConsume))
                )
        );
    }

    /**
     * Notify the message encoder that the player wants to remove a resource from its warehouse
     * @param position resource position in the deck in which the player wants to remove the resource
     */
    public void removeResource(ResourcePosition position){
        executeIfCurrentPlayer(
                ()->notify(new RemoveResourceMessage(position))
        );
    }

    /**
     * Notify the message encoder that the player wants to pick some resources from the marble market
     * @param selection selection from the marble market
     * @param positions list of positions in which user wants to put the bought resources
     * @param conversions list of possible white marbles conversions chosen by the player
     */
    public void pickResources(MarbleSelection selection, ArrayList<ResourcePosition> positions, ArrayList<ResourceType> conversions){
        executeIfCurrentPlayer(
                ()->executeIfNotMainActionYet(
                        ()->notify(new PickResourcesMessage(selection, positions, conversions))
                )
        );
    }

    /**
     * Notify the message encoder that the player wants to produce
     * @param consumed resources used for this production action
     * @param ids list of development cards ids used for this production action
     * @param genericProduction optional generic production
     * @param leadersProductions list of productions from activated leader cards with production effects
     */
    public void produceResources(ConsumeTarget consumed, ArrayList<String> ids, Optional<ProductionEffect> genericProduction, ArrayList<ProductionEffect> leadersProductions){
        executeIfCurrentPlayer(
                () -> executeIfNotMainActionYet(
                        ()-> {
                            ArrayList<ProductionEffect> toActivate = game.getPlayerBoardMap()
                                    .get(game.getCurrentPlayer()).getDevelopmentCards().getProductionAvailable(ids);
                            genericProduction.ifPresent(toActivate::add);
                            toActivate.addAll(leadersProductions);
                            notify(new ProductionMessage(consumed, toActivate));
                        }
                )
        );
    }

    /**
     * Manages the player's pick resources from marble market action by finding all player's active leader cards
     * with depot effects, leader cards with marble conversions effects and tells the UI to show this information in order
     * to enable the player to make its choice
     * @param selection selection from the marble market
     */
    public void pickCommandHandler(MarbleSelection selection){
        executeIfCurrentPlayer(
                ()->executeIfNotMainActionYet(
                        ()->{
                            try{
                                ArrayList<ClientLeaderCard> changeEffectsCards = new ArrayList<>();
                                ArrayList<ClientLeaderCard> depotEffectsCards;
                                ArrayList<Marble> selected = game.getClientMarbleMarket().getSelectedMarbles(selection);
                                if(selected.stream().map(Marble::getResourceType).collect(Collectors.toList()).contains(ResourceType.BLANK)){
                                    changeEffectsCards = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                                            .getClientLeaderCards().getActiveCardByEffect(EffectType.CHANGE);
                                }
                                depotEffectsCards = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                                        .getClientLeaderCards().getActiveCardByEffect(EffectType.DEPOT);
                                userInterface.displayPickResourceMenu(selection, selected, changeEffectsCards, depotEffectsCards);
                            } catch (Exception e){
                                userInterface.displayError("Cannot retrieve marbles from that position!");
                            }
                        }
                )
        );
    }

    /**
     * Manages the player's buy development card action by finding all player's active leader cards with depot effects,
     * leader cards with discount effects and tells the UI to show this information in order to enable the player to
     * make its choice
     * @param id the id of the development card that the player wants to buy
     */
    public void buyCommandHandler(String id){
        executeIfCurrentPlayer(
                ()->executeIfNotMainActionYet(
                        ()->{
                            if(game.getClientCardsMarket().getCardById(id)==null){
                                userInterface.displayError("Cannot buy card with that id!");
                            } else {
                                ArrayList<ClientLeaderCard> discountsCards = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                                        .getClientLeaderCards().getActiveCardByEffect(EffectType.DISCOUNT);
                                ArrayList<ClientLeaderCard> additionalDepotsCards = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                                        .getClientLeaderCards().getActiveCardByEffect(EffectType.DEPOT);
                                userInterface.displayBuyDevelopmentCardMenu(id, discountsCards, additionalDepotsCards);
                            }
                        }
                )
        );
    }

    /**
     * Manages the player's production action by finding all player's active leader cards with production effects,
     * leader cards with depot effects and tells the UI to show this information in order to enable the player
     * to make its choice
     */
    public void produceCommandHandler(){
        executeIfCurrentPlayer(
                ()->executeIfNotMainActionYet(
                        ()->{
                            ArrayList<ClientLeaderCard> leaderProductionsCard = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                                    .getClientLeaderCards().getActiveCardByEffect(EffectType.PRODUCTION);
                            ArrayList<ClientLeaderCard> additionalDepotsCard = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                                    .getClientLeaderCards().getActiveCardByEffect(EffectType.DEPOT);
                            userInterface.displayProduceMenu(leaderProductionsCard, additionalDepotsCard);
                        }
                )
        );
    }

    /**
     * Tells the UI to show a specific playerboard given the username as parameter
     * @param username the name of the playerboard whose playerboard has to be shown
     */
    public void displayPlayerboardByUsername(String username){
        ClientPlayerBoard selectedPlayerboard = getGame().getPlayerBoardMap().get(username);
        if(userInterface instanceof GUI) {
            ((GUI)userInterface).setPlayerBoardUsername(username, username.equals(getGame().getMyUsername()));
        }
        userInterface.displayFaithPath(selectedPlayerboard.getFaithPath(), username);
        userInterface.displayWarehouse(selectedPlayerboard.getWarehouse(), username);
        userInterface.displayLeaderCardDeck(selectedPlayerboard.getClientLeaderCards(), username);
        userInterface.displayStrongBox(selectedPlayerboard.getStrongbox(), username);
        userInterface.displayDevelopmentCardDecks(selectedPlayerboard.getDevelopmentCards(), username);
    }

    /**
     * Tells the UI to display the info that Lorenzo Il Magnifico has won the game
     */
    public void setMagnificoAsWinner(){
        userInterface.displayInfo("Magnifico has won! GAME OVER");
    }

    /**
     * Tells the UI to start itself and initializing it by passing the client game
     */
    public void startUI(){
        userInterface.startUI(getGame());
    }

    /**
     * Tells the UI to display that the game is ended
     */
    public void endGame(){
        userInterface.showGameIsEnded();
    }

    /**
     * Updates the victory points for a specific player
     * @param points updated victory points
     * @param username name of the player with those victory points
     */
    public void updateVictoryPoints(int points, String username){
        game.getPlayerBoardMap().get(username).setVictoryPoints(points);
    }

}
