package it.polimi.ingsw.client.view.ui.gui.controllers;

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

public class StatsController {

    GridPane statsPane;
    private final ObservableList<Node> statsList;
    private String localUsername = "";
    private final Map<String, Integer> otherPlayersOrder;

    /**
     * Create a stats controller for GUI
     * @param statsPane Grid pane in which player stats are located
     */
    public StatsController(GridPane statsPane){
        otherPlayersOrder = new HashMap<>();
        statsList = statsPane.getChildren();
    }

    /**
     * Refreshes statistics view for the user identified by the given playerboard
     * @param playerBoard the playerboard of the player whose statistics need to be updated
     */
    public void initStats(ClientPlayerBoard playerBoard){
        // FIXME victory points?
        if(playerBoard.getOrder() == 0){
            localUsername = playerBoard.getUsername();
        }else{
            otherPlayersOrder.put(playerBoard.getUsername(), playerBoard.getOrder());
        }
        AnchorPane playerPane = (AnchorPane)statsList.get(playerBoard.getOrder());
        Label playerUsername = null;
        TextField playerVp = null, playerFp = null, playerDc = null;
        if(playerBoard.isMine()){
            // Get proper player name label
            playerUsername = ((Label)((GridPane)playerPane.getChildren().get(0)).getChildren().get(0).lookup("#local-username"));
            // Get proper player victory points
            playerVp = ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(4).lookup("#local-vp"));
            // Get proper player faith points
            playerFp = ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(5).lookup("#local-fp"));
            // Get proper player development cards number
            playerDc = ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(6).lookup("#local-dc"));

        }
        else{
            int playerOrder = playerBoard.getOrder()+1;
            playerUsername = ((Label)((GridPane)playerPane.getChildren().get(0)).getChildren().get(0).lookup("#username-" + playerOrder));
            playerVp = ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(4).lookup("#vp-" + playerOrder));
            playerFp = ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(5).lookup("#fp-" + playerOrder));
            playerDc = ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(6).lookup("#dc-" + playerOrder));
        }
        playerUsername.setText(playerBoard.getUsername());
        playerVp.setText(String.valueOf(playerBoard.getFaithPath().getFaithPoints()));
        playerFp.setText(String.valueOf(playerBoard.getFaithPath().getFaithPoints()));
        playerDc.setText(String.valueOf(playerBoard.getDevelopmentCards().getCardsNumber()));
    }

    public void refreshFaithPoints(int faithPoints, String playerUsername){
        String paneId;
        AnchorPane playerPane;
        if(playerUsername.equals(localUsername)){
            paneId = "#local-fp";
            playerPane = (AnchorPane)statsList.get(0);
        }else{
            paneId = "#fp-" + otherPlayersOrder.get(playerUsername) + 1;
            playerPane = (AnchorPane)statsList.get(otherPlayersOrder.get(playerUsername));
        }
        ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(5).lookup(paneId)).setText(String.valueOf(faithPoints));
    }

    public void refreshVictoryPoints(int victoryPoints, String playerUsername){
        String paneId;
        AnchorPane playerPane;
        if(playerUsername.equals(localUsername)){
            paneId = "#local-vp";
            playerPane = (AnchorPane)statsList.get(0);
        }else{
            paneId = "#vp-" + otherPlayersOrder.get(playerUsername) + 1;
            playerPane = (AnchorPane)statsList.get(otherPlayersOrder.get(playerUsername));
        }
        ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(5).lookup(paneId)).setText(String.valueOf(victoryPoints));
    }

    public void refreshDevelopmentCardNumbers(int cardNumbers, String playerUsername){
        String paneId;
        AnchorPane playerPane;
        if(playerUsername.equals(localUsername)){
            paneId = "#local-dc";
            playerPane = (AnchorPane)statsList.get(0);
        }else{
            paneId = "#dc-" +   otherPlayersOrder.get(playerUsername)+ 1;
            playerPane = (AnchorPane)statsList.get(otherPlayersOrder.get(playerUsername));
        }
        ((TextField)((GridPane)playerPane.getChildren().get(0)).getChildren().get(5).lookup(paneId)).setText(String.valueOf(cardNumbers));
    }
}
