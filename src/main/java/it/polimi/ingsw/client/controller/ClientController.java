package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.model.ClientGame;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_client.ChosenInitialLeadersMessage;
import it.polimi.ingsw.network.message.from_client.ChosenInitialResourcesMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
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
                                    .map(card -> card.getId())
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

    public void refreshWarehouse(ArrayList<ResourceDepot> resourceDepots, String player){
        this.getGame().getPlayerBoardMap().get(player).getWarehouse().setResourceDepots(resourceDepots);
    }

    public void startListening(){
        this.getGame().setGameStarted(true);
        userInterface.startListening(this);
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
}
