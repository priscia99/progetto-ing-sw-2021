package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_client.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.ChangeEffect;
import it.polimi.ingsw.server.model.card.effect.EffectType;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ClientController extends Observable<Message<ServerController>> {

    private final ClientGame game;
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

    public void chooseInitialResources(int toChoose, String username){
        if(username.equals(game.getMyUsername())) {
            notify(new ChosenInitialResourcesMessage(userInterface.chooseInitialResources(toChoose), game.getMyUsername()));
        }
    }

    public void chooseInitialLeaders(String username){
        if(username.equals(game.getMyUsername())){
            notify(new ChosenInitialLeadersMessage(
                    userInterface.chooseInitialLeaders(
                            game.getPlayerBoardMap().get(game.getMyUsername()).getClientLeaderCards().getClientLeaderCards()
                                    .stream()
                                    .map(ClientCard::getId)
                                    .collect(Collectors.toCollection(ArrayList::new))),
                    game.getMyUsername()));
        }
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
        this.getGame().getPlayerBoardMap().get(player).getFaithPath().setPopeFavors(popeFavors);
        this.getGame().getPlayerBoardMap().get(player).getFaithPath().setFaithPoints(faithPoints);
        this.getGame().getPlayerBoardMap().get(player).getFaithPath().show(displayToView);
    }

    public void refreshWarehouse(ArrayList<ResourceDepot> resourceDepots, String player){
        this.getGame().getPlayerBoardMap().get(player).getWarehouse().setResourceDepots(resourceDepots);
    }

    public void refreshCardMarket(ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks){
        this.getGame().getClientCardsMarket().setDecks(decks);
    }

    public void startListening(){
        this.getGame().setGameStarted(true);
        userInterface.setController(this);
        userInterface.startListening();
    }

    public void viewLeaderCards(String player){
        game.getPlayerBoardMap().get(player).getClientLeaderCards().show();
    }

    public void viewDevelopmentCards(String player){
        game.getPlayerBoardMap().get(player).getDevelopmentCards().show();
    }

    public void viewDevelopmentCards(){
        game.getPlayerBoardMap().get(game.getCurrentPlayer()).getDevelopmentCards().show();
    }

    public void viewWarehouse(String player){
        game.getPlayerBoardMap().get(player).getWarehouse().show();
    }

    public void viewWarehouse(){
        game.getPlayerBoardMap().get(game.getCurrentPlayer()).getWarehouse().show();
    }

    public void viewStrongbox(String player){
        game.getPlayerBoardMap().get(player).getStrongbox().show();
    }

    public void viewStrongbox(){
        game.getPlayerBoardMap().get(game.getCurrentPlayer()).getStrongbox().show();
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

    public void activateLeaderCard(String cardId){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            notify(new PlayLeaderCardMessage(cardId));
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void dropLeaderCard(String cardId){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            notify(new DropLeaderCardMessage(cardId));
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void endTurn(){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            notify(new EndTurnMessage());
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void swapDepots(int first, int second){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            notify(new SwapDepotsMessage(first, second));
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void buyDevelopmentCard(String cardId, int deckIndex,  HashMap<ResourcePosition, ResourceStock> toConsume){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
           notify(new BuyDevelopmentCardMessage(cardId, deckIndex,toConsume ));
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void pickResources(MarbleSelection selection, ArrayList<ResourcePosition> positions, ArrayList<ResourceType> conversions){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            notify(new PickResourcesMessage(selection, positions, conversions));
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void produceResources(HashMap<ResourcePosition, ResourceStock> consumed, ArrayList<String> ids){
        if(game.getCurrentPlayer().equals(game.getMyUsername())){
            ArrayList<ProductionEffect> toActivate = game.getPlayerBoardMap()
                    .get(game.getCurrentPlayer()).getDevelopmentCards().getProductionAvailable(ids);
            notify(new ProductionMessage(consumed, toActivate));
        } else {
            userInterface.displayError("This action is available only for current turn player!");
        }
    }

    public void pickCommandHandler(MarbleSelection selection){
        try{
            ArrayList<ChangeEffect> changeEffects = new ArrayList<>();
            ArrayList<Marble> selected = game.getClientMarbleMarket().getSelectedMarbles(selection);
            if(selected.stream().map(Marble::getResourceType).collect(Collectors.toList()).contains(ResourceType.BLANK)){
                ArrayList<ClientLeaderCard> cards = game.getPlayerBoardMap().get(game.getCurrentPlayer()).getClientLeaderCards().getClientLeaderCards();
                for(ClientLeaderCard card : cards){
                    if(card.isActive() && card.getEffect().getEffectType().equals(EffectType.CHANGE)){
                        changeEffects.add((ChangeEffect) card.getEffect());
                    }
                }

            }
            userInterface.displayPickResourceMenu(selection, selected, changeEffects);
        } catch (Exception e){
            userInterface.displayError("Cannot retrieve marbles from that position!");
        }
    }
}
