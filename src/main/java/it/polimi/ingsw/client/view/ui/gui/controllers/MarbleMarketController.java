package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientMarbleMarket;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
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

    private static final String BLUE_MARBLE_PATH = "/img/ico/blue_marble.png";          // shield
    private static final String GRAY_MARBLE_PATH = "/img/ico/gray_marble.png";          // stone
    private static final String PURPLE_MARBLE_PATH = "/img/ico/purple_marble.png";      // servant
    private static final String RED_MARBLE_PATH = "/img/ico/red_marble.png";            // faith
    private static final String WHITE_MARBLE_PATH = "/img/ico/white_marble.png";        // white
    private static final String YELLOW_MARBLE_PATH = "/img/ico/yellow_marble.png";      // coin

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
        String marbleImagePath = null;
        Image marbleImage = null;
        BackgroundImage bgImg = null;

        // refreshing not for sale marble
        marbleImagePath = this.getAssetLink(marbleMarket.getNotForSale());
        notForSaleMarble.setStyle("-fx-background-image: url(" + marbleImagePath + ");");

        // refreshing all other marbles
        Marble[][] marketPositions = marbleMarket.getOnSale();

        // iterate through market rows
        for(int i=0; i<marketPositions.length; i++){
            // iterate through marble cols
            for(int j=0; j<marketPositions[i].length; j++){
                Marble tempMarble = marketPositions[i][j];  // getting temp marble
                Pane tempPane = (Pane) marbleMarketPane.lookup("#mm-" + String.valueOf(i+1) + "-" + String.valueOf(j+1));
                marbleImagePath = this.getAssetLink(tempMarble);
                tempPane.setStyle("-fx-background-image: url(" + marbleImagePath + ");");

            }
        }
        tabPane.requestLayout();
    }

    public void enableHandlers(){
        for (String btnID : marbleMarketButtons.keySet()){
            Button tempBtn = marbleMarketButtons.get(btnID);
            tempBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, onPressedMarketChoice);
        }
    }

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
     * Retrieve the proper asset link based on marble type
     * @param marble Marble to return path
     * @return Image asset link
     */
    private String getAssetLink(Marble marble){
        String path = null;
        switch (marble.getResourceType()){
            case SHIELD:    path = BLUE_MARBLE_PATH;
                            break;
            case COIN:      path = YELLOW_MARBLE_PATH;
                            break;
            case STONE:     path = GRAY_MARBLE_PATH;
                            break;
            case SERVANT:   path = PURPLE_MARBLE_PATH;
                            break;
            case FAITH:     path = RED_MARBLE_PATH;
                            break;
            case BLANK:     path = WHITE_MARBLE_PATH;
                            break;
        }
        return path;
    }

    public void setMarbleMarket(ClientMarbleMarket market){
        this.marbleMarket = market;
        this.refreshMarbleMarket();
    }
}
