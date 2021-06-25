package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_client.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.*;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.RunnableFuture;
import java.util.stream.Collectors;

public class ClientController extends Observable<Message<ServerController>> {

    private final ClientGame game;
    private String selectedPlayerBoard = null;
    UI userInterface;

    public ClientController(ClientGame game, UI userInterface) {
        this.game = game;
        this.userInterface = userInterface;
    }

    public void setCurrentPlayer(String player){
        game.setCurrentPlayer(player);
    }

    public void setMainActionDone(boolean done){
        game.setMainActionDone(done);
    }

    public ClientGame getGame() {
        return game;
    }

    public void viewChooseInitialResources(int toChoose, String username){
        if(username.equals(game.getMyUsername())) {
            userInterface.displayChooseInitialResourcesMenu(toChoose);
        }
    }

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

    public void chooseInitialResources(HashMap<ResourcePosition, ResourceType> resources){
            notify(new ChosenInitialResourcesMessage(resources, game.getMyUsername()));
    }

    public void chooseInitialLeaders(ArrayList<String> selected){
            notify(new ChosenInitialLeadersMessage(selected, game.getMyUsername()));
    }

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

    public void refreshFaithPath(ArrayList<Boolean> popeFavors, int blackCrossPosition){
        userInterface.displayInfo("Lorenzo Il Magnifico's faith points increased!");
        ClientFaithPath faithPath = this.getGame().getPlayerBoardMap().get(game.getCurrentPlayer()).getFaithPath();
        faithPath.setPopeFavors(popeFavors);
        faithPath.setBlackCrossPosition(blackCrossPosition);
        faithPath.show(true);
    }

    public void refreshWarehouse(ArrayList<ResourceDepot> resourceDepots, String player){
        this.getGame().getPlayerBoardMap().get(player).getWarehouse().setResourceDepots(resourceDepots);
    }

    public void refreshCardMarket(ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks){
        this.getGame().getClientCardsMarket().setDecks(decks);
    }

    public void startListening(){
        this.getGame().setGameStarted(true);
        userInterface.startListening();
    }

    public void viewLeaderCards(String player){
        game.getPlayerBoardMap().get(player).getClientLeaderCards().show();
    }

    public void viewDevelopmentCards(String player){
        game.getPlayerBoardMap().get(player).getDevelopmentCards().show();
    }


    public void viewWarehouse(String player){
        game.getPlayerBoardMap().get(player).getWarehouse().show();
    }

    public void viewStrongbox(String player){
        game.getPlayerBoardMap().get(player).getStrongbox().show();
    }


    public void viewFaithPath(String player){
        game.getPlayerBoardMap().get(player).getFaithPath().show();
    }

    public void viewOtherPlayersUsername(){
        ArrayList<String> usernames = new ArrayList<>(game.getPlayerBoardMap().keySet());
        userInterface.displayOtherPlayersUsername(usernames);
    }

    public void viewHelpMessage(){
        userInterface.displayHelpMessage();
    }

    public void viewTurnInfo(){
        userInterface.displayTurnInfo(new ArrayList<>(game.getPlayerBoardMap().keySet()), game.getCurrentPlayer());
    }

    public void viewMarbleMarket(){
        userInterface.displayMarbleMarket(game.getClientMarbleMarket());
    }

    public void viewCardMarket(){
        userInterface.displayCardMarket(game.getClientCardsMarket());
    }

    public void viewPossibleActions(){
        boolean isMyTurn = game.getCurrentPlayer().equals(game.getMyUsername());
        userInterface.displayPossibleActions(isMyTurn, game.isMainActionDone());
    }

    public void viewErrorMessage(String message){
        userInterface.displayError(message);
    }

    private void executeIfCurrentPlayer(Runnable action){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            action.run();
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void activateLeaderCard(String cardId){
        executeIfCurrentPlayer(()->notify(new PlayLeaderCardMessage(cardId)));
    }

    public void dropLeaderCard(String cardId){
        executeIfCurrentPlayer(()->notify(new DropLeaderCardMessage(cardId)));
    }

    public void endTurn(){
        executeIfCurrentPlayer(()->notify(new EndTurnMessage()));
    }

    public void swapDepots(int first, int second){
        executeIfCurrentPlayer(()->notify(new SwapDepotsMessage(first, second)));
    }

    public void buyDevelopmentCard(String cardId, int deckIndex,  ConsumeTarget toConsume){
        executeIfCurrentPlayer(()->notify(new BuyDevelopmentCardMessage(cardId, deckIndex,toConsume)));
    }

    public void pickResources(MarbleSelection selection, ArrayList<ResourcePosition> positions, ArrayList<ResourceType> conversions){
        executeIfCurrentPlayer(()->notify(new PickResourcesMessage(selection, positions, conversions)));
    }

    public void produceResources(ConsumeTarget consumed, ArrayList<String> ids, Optional<ProductionEffect> genericProduction, ArrayList<ProductionEffect> leadersProductions){
        executeIfCurrentPlayer(()-> {
                    ArrayList<ProductionEffect> toActivate = game.getPlayerBoardMap()
                            .get(game.getCurrentPlayer()).getDevelopmentCards().getProductionAvailable(ids);
                    genericProduction.ifPresent(toActivate::add);
                    toActivate.addAll(leadersProductions);
                    notify(new ProductionMessage(consumed, toActivate));
            }
        );
    }

    public void pickCommandHandler(MarbleSelection selection){
        executeIfCurrentPlayer(()->{
            try{
                ArrayList<ChangeEffect> changeEffects = new ArrayList<>();
                ArrayList<DepotEffect> depotEffects;
                ArrayList<Marble> selected = game.getClientMarbleMarket().getSelectedMarbles(selection);
                if(selected.stream().map(Marble::getResourceType).collect(Collectors.toList()).contains(ResourceType.BLANK)){
                    changeEffects = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                            .getClientLeaderCards().getActiveEffects(EffectType.CHANGE);
                }
                depotEffects = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                        .getClientLeaderCards().getActiveEffects(EffectType.DEPOT);
                userInterface.displayPickResourceMenu(selection, selected, changeEffects, depotEffects);
            } catch (Exception e){
                userInterface.displayError("Cannot retrieve marbles from that position!");
            }
        });
    }

    public void buyCommandHandler(String id){
        executeIfCurrentPlayer(()->{
            if(game.getClientCardsMarket().getCardById(id)==null){
                userInterface.displayError("Cannot buy card with that id!");
            } else {
                ArrayList<DiscountEffect> discounts = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                        .getClientLeaderCards().getActiveEffects(EffectType.DISCOUNT);
                ArrayList<DepotEffect> additionalDepots = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                        .getClientLeaderCards().getActiveEffects(EffectType.DEPOT);
                userInterface.displayBuyDevelopmentCardMenu(id, discounts, additionalDepots);
            }
        });
    }

    public void produceCommandHandler(){
        executeIfCurrentPlayer(()->{
            ArrayList<ProductionEffect> leaderProductions = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                    .getClientLeaderCards().getActiveEffects(EffectType.PRODUCTION);
            ArrayList<DepotEffect> additionalDepots = game.getPlayerBoardMap().get(game.getCurrentPlayer())
                    .getClientLeaderCards().getActiveEffects(EffectType.DEPOT);
            userInterface.displayProduceMenu(leaderProductions, additionalDepots);
        });
    }

    public void displayPlayerboardByUsername(String username){
        ClientPlayerBoard selectedPlayerboard = getGame().getPlayerBoardMap().get(username);
        if(userInterface instanceof GUI) {
            ((GUI)userInterface).setPlayerBoardUsername(username, username.equals(getGame().getMyUsername()));
        }
        userInterface.displayFaithPath(selectedPlayerboard.getFaithPath());
        userInterface.displayWarehouse(selectedPlayerboard.getWarehouse());
        userInterface.displayLeaderCardDeck(selectedPlayerboard.getClientLeaderCards());
        userInterface.displayStrongBox(selectedPlayerboard.getStrongbox());
        userInterface.displayDevelopmentCardDecks(selectedPlayerboard.getDevelopmentCards());
    }
    public void setMagnificoAsWinner(){
        userInterface.displayInfo("Magnifico has won! GAME OVER");
    }

    public void startUI(){
        userInterface.startUI(getGame());
    }
}
