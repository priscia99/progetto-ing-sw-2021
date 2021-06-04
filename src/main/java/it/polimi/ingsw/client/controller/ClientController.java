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

import java.util.ArrayList;
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

    public void refreshLeaderCards(ArrayList<ClientLeaderCard> cards) {
        String currentPlayer = this.getGame().getCurrentPlayer();
        this.getGame().getPlayerBoardMap().get(currentPlayer).getClientLeaderCards().setClientLeaderCards(cards);
    }

    public void refreshLeaderCards(ArrayList<ClientLeaderCard> cards, String player) {
        System.out.println(player + "'s leader cards have changed");
        this.getGame().getPlayerBoardMap().get(player).getClientLeaderCards().setClientLeaderCards(cards);
    }

    public void refreshWarehouse(ArrayList<ResourceDepot> resourceDepots, String player){
        this.getGame().getPlayerBoardMap().get(player).getWarehouse().setResourceDepots(resourceDepots);
    }
}
