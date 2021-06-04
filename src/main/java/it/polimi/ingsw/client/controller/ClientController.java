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

    public void executeCommand(String command){
        if(command.contains("help")){
            // TODO fill help
        }
        else if(command.contains("displayleadercards")){
            game.getPlayerBoardMap().get(game.getMyUsername()).getClientLeaderCards().show();
        }
        else{
            userInterface.displayError("Command not valid");
        }
    }

    public void viewContent(HashMap<String, String> inputParameters){
        String targetedPlayer = inputParameters.get("p");
        String targeredContent = inputParameters.get("v");
        // leadercards -- developmentcards -- warehouse -- strongbox -- faithpath
        try{
            switch(targeredContent){
                case "leadercards":
                    game.getPlayerBoardMap().get(targetedPlayer).getClientLeaderCards().show();
                    break;
                case "developmentcards":
                    game.getPlayerBoardMap().get(targetedPlayer).getDevelopmentCards().show();
                    break;
                case "warehouse":
                    game.getPlayerBoardMap().get(targetedPlayer).getWarehouse().show();
                    break;
                case "strongbox":
                    game.getPlayerBoardMap().get(targetedPlayer).getStrongbox().show();
                    break;
                case "faithpath":
                    game.getPlayerBoardMap().get(targetedPlayer).getFaithPath().show();
                    break;
                default:
                    userInterface.displayError("Input parameters are not correct");
                    userInterface.displayError("Choose between -v [leadercards | developmentcards | warehouse | strongbox | faithpath]");
            }
        }catch (Exception e){
            StringBuilder playersList = new StringBuilder();
            game.getPlayerBoardMap().keySet().stream().forEach(player -> playersList.append(player).append(" | "));
            playersList.delete(playersList.length()-3, playersList.length());
            // un-comment print stack trace if player is valid -> it's a serious error
            // e.printStackTrace();
            userInterface.displayError("The targeted player doesn't exixsts");
            userInterface.displayError("Choose between -p [" + playersList + "]");
        }
    }
}
