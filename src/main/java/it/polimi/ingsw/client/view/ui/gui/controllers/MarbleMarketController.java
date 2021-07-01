package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientMarbleMarket;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import it.polimi.ingsw.server.model.marble.Marble;
import it.polimi.ingsw.server.model.marble.MarbleSelection;
import it.polimi.ingsw.server.model.marble.Orientation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Map;
import java.util.Objects;

public class MarbleMarketController extends GenericGUIController{



    private GridPane marbleMarketPane;
    private Pane notForSaleMarble;
    private TabPane tabPane;
    private ClientMarbleMarket marbleMarket;
    private Map<String, Button> marbleMarketButtons;
    private boolean canUserDoAction;

    public MarbleMarketController(ClientController clientController, GridPane marbleMarketPane, Pane notForSaleMarble, TabPane tabPane, Map<String, Button> marbleMarketButtons) {
        super(clientController);
        this.marbleMarketPane = marbleMarketPane;
        this.notForSaleMarble = notForSaleMarble;
        this.tabPane = tabPane;
        this.marbleMarketButtons = marbleMarketButtons;
        this.canUserDoAction = false;
    }

    /**
     * Refreshes marble market pane based on current market representation
     *
     */
    public void refreshMarbleMarket(){
        String marbleImagePath ;

        // refreshing not for sale marble
        marbleImagePath = AssetsHelper.getMarblePath(marbleMarket.getNotForSale());
        notForSaleMarble.setStyle("-fx-background-image: url(" + marbleImagePath + ");");

        // refreshing all other marbles
        Marble[][] marketPositions = marbleMarket.getOnSale();

        // iterate through market rows
        for(int i=0; i<marketPositions.length; i++){
            // iterate through marble cols
            for(int j=0; j<marketPositions[i].length; j++){
                Pane tempPane = (Pane) marbleMarketPane.lookup("#mm-" + (i+1) + "-" + (j+1));
                marbleImagePath = AssetsHelper.getMarblePath(marketPositions[i][j]);
                FXHelper.setBackground(tempPane, marbleImagePath);
            }
        }
        tabPane.requestLayout();
    }

    /**
     * Add the proper handlers to the marble market arrows
     */
    public void enableHandlers(){
        for (String btnID : marbleMarketButtons.keySet()){
            Button tempBtn = marbleMarketButtons.get(btnID);
            tempBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, onPressedMarketChoice);
        }
    }

    /**
     * Handler that is triggered when a market arrow button is pressed
     * This handler tells the controller that a market row/column is chosen
     */
    private final EventHandler<MouseEvent> onPressedMarketChoice = event -> {
        SceneController.startMainAction();
        Orientation choiceOrientation;
        int choiceIndex;
        Button pressedBtn = (Button) event.getSource();
        String parsedChoice[] = pressedBtn.getId().split("-");

        if(parsedChoice[1].equals("r")){
            choiceOrientation = Orientation.HORIZONTAL;
        }else{
            choiceOrientation = Orientation.VERTICAL;
        }
        choiceIndex = Integer.parseInt(parsedChoice[2]);

        // returning to client controller handling the marble market selection
        super.getClientController().pickCommandHandler(new MarbleSelection(choiceOrientation, choiceIndex-1));
    };

    /**
     * Sets the marble market tab
     * @param market game marble market
     */
    public void setMarbleMarket(ClientMarbleMarket market){
        this.marbleMarket = market;
        this.refreshMarbleMarket();
    }
}
