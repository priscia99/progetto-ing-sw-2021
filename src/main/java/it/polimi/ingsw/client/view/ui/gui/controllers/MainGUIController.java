package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientLeaderCardDeck;
import it.polimi.ingsw.client.model.ClientPlayerBoard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Text;

import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainGUIController {
    @FXML
    private GridPane statsPane;
    private ObservableList<Node> statsList;
    @FXML
    private AnchorPane statsAnchorPane;
    @FXML
    private GridPane playerStatsPane;
    @FXML
    private MenuButton playersChoicePicker;
    public MainGUIController(){}

    @FXML
    public void initialize(){
        statsList = statsPane.getChildren();
        System.out.println("Initializing MainGUIController process");
    }

    public void work(){
        System.out.println(statsPane);
    }

    /**
     * Refreshes statistics view for the user identified by the given playerboard
     * @param playerBoard the playerboard of the player whose statistics need to be updated
     */
    public void refreshStats(ClientPlayerBoard playerBoard){
        // FIXME victory points?
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

    /**
     * Initialize the playerboard choice picker
     * @param players
     */
    public void initMenuChoicePicker(ArrayList<String> players){
        players.stream().forEach(playerName -> playersChoicePicker.getItems().add(new MenuItem(playerName)));
    }

    public void refreshLeaderCards(ClientLeaderCardDeck deck){

    }
}
