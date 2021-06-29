package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientPlayerBoard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class StatsController extends GenericGUIController {

    GridPane statsPane;
    private final ObservableList<Node> statsList;
    private String localUsername = "";
    private final Map<String, Integer> playerOrders;
    private GridPane firstPlayerStatsPane, secondPlayerStatsPane, thirdPlayerStatsPane, fourthPlayerStatsPane;
    private String myUsername;

    /**
     * Create a stats controller for GUI
     * @param statsPane Grid pane in which player stats are located
     */
    public StatsController(ClientController clientController, GridPane statsPane, GridPane firstPlayerStatsPane, GridPane secondPlayerStatsPane, GridPane thirdPlayerStatsPane, GridPane fourthPlayerStatsPane){
        super(clientController);
        playerOrders = new HashMap<>();
        statsList = statsPane.getChildren();
        this.firstPlayerStatsPane = firstPlayerStatsPane;
        this.secondPlayerStatsPane = secondPlayerStatsPane;
        this.thirdPlayerStatsPane = thirdPlayerStatsPane;
        this.fourthPlayerStatsPane = fourthPlayerStatsPane;
        this.myUsername = null;
    }

    /**
     * Refreshes statistics view for the user identified by the given playerboard
     * @param playerBoard the playerboard of the player whose statistics need to be updated
     */
    public void initStats(ClientPlayerBoard playerBoard){
        if(playerBoard.isMine()){
            this.myUsername = playerBoard.getUsername();
        }
        if(!playerOrders.containsKey(playerBoard.getUsername())){
            playerOrders.put(playerBoard.getUsername(), playerBoard.getOrder());
        }
        GridPane selectedGridPane = getPaneByOrder(playerBoard.getOrder());
        int playerOrder = playerBoard.getOrder() + 1;
        if(myUsername != null && myUsername.equals(playerBoard.getUsername())) {
            ((Label) selectedGridPane.lookup("#username-" + playerOrder)).setText("[YOU] " + playerBoard.getUsername());
        }
        else{
            ((Label) selectedGridPane.lookup("#username-" + playerOrder)).setText(playerBoard.getUsername());
        }
        ((TextField)selectedGridPane.lookup("#fp-" + playerOrder)).setText(String.valueOf(playerBoard.getFaithPath().getFaithPoints()));
        ((TextField)selectedGridPane.lookup("#dc-" + playerOrder)).setText(String.valueOf(playerBoard.getDevelopmentCards().getCardsNumber()));
        ((TextField)selectedGridPane.lookup("#vp-" + playerOrder)).setText(String.valueOf(playerBoard.getVictoryPoints()));

    }

    private GridPane getPaneByOrder(int order){
        GridPane selectedGridPane = null;
        switch (order){
            case 0 -> selectedGridPane = firstPlayerStatsPane;
            case 1 -> selectedGridPane = secondPlayerStatsPane;
            case 2 -> selectedGridPane = thirdPlayerStatsPane;
            case 3 -> selectedGridPane = fourthPlayerStatsPane;
        }
        return selectedGridPane;
    }

    public void refreshFaithPoints(int faithPoints, String playerUsername){
        if(playerOrders.containsKey(playerUsername)){
            int order = playerOrders.get(playerUsername);
            GridPane playerPane = getPaneByOrder(order);
            ((TextField)playerPane.lookup("#fp-" + (order+1))).setText(String.valueOf(faithPoints));
        }
    }

    public void refreshVictoryPoints(int victoryPoints, String playerUsername){
        if(playerOrders.containsKey(playerUsername)){
            int order = playerOrders.get(playerUsername);
            GridPane playerPane = getPaneByOrder(order);
            ((TextField)playerPane.lookup("#vp-" + (order+1))).setText(String.valueOf(victoryPoints));
        }
    }

    public void refreshDevelopmentCardNumbers(int cardNumbers, String playerUsername){
        if(playerOrders.containsKey(playerUsername)){
            int order = playerOrders.get(playerUsername);
            GridPane playerPane = getPaneByOrder(order);
            ((TextField)playerPane.lookup("#dc-" + (order+1))).setText(String.valueOf(cardNumbers));
        }
    }

    public void refreshTurn(String playerInTurn){
        playerOrders.entrySet().forEach(
                entry -> {
                    int order = playerOrders.get(entry.getKey());
                    String tempUsername = entry.getKey();
                    GridPane playerPane = getPaneByOrder(order);
                    if(tempUsername.equals(playerInTurn)){
                        if(myUsername.equals(entry.getKey())){
                            ((Label) playerPane.lookup("#username-" + (order+1))).setText("[YOU] " + playerInTurn + " (In turn)");
                        }
                        else{
                            ((Label) playerPane.lookup("#username-" + (order+1))).setText(playerInTurn + " (In turn)");
                        }
                    }else{
                        if(myUsername.equals(entry.getKey())){
                            ((Label) playerPane.lookup("#username-" + (order+1))).setText("[YOU] " + entry.getKey());
                        }
                        else{
                            ((Label) playerPane.lookup("#username-" + (order+1))).setText(entry.getKey());
                        }
                    }
                }
        );
    }

}
