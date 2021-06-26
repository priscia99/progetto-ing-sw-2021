package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientFaithPath;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Objects;

public class FaithPathController extends GenericGUIController {
    private static int CELLS_NUMBER = 24;                           // number of faith path cells
    private static final String CROSS_PATH = "/img/croce.png";      // cross image path
    private static final String LORENZO_PATH = "/img/lorenzo.png";
    private static final String CROSSES_PATH = "/img/crosses.png";
    GridPane faithPathPane;                                         // faith path generic pane
    ObservableList<Node> cellsPaneList;                             // list of position panes

    public FaithPathController(ClientController clientController, GridPane faithPathPane) {
        super(clientController);
        this.faithPathPane = faithPathPane;
        this.cellsPaneList = faithPathPane.getChildren();
        cellsPaneList.remove(0);    // remove title label from children list
    }

    /**
     * Refreshes faith path and display cross position based on current faith points
     * @param faithPath Player's faith path
     */
    public void refreshFaithPath(ClientFaithPath faithPath){
        for(int i=0; i<CELLS_NUMBER; i++){
            Pane tempCell = (Pane) faithPathPane.lookup("#fc-" + i);
            String imageUrl = null;
            if(i==faithPath.getBlackCrossPosition() && i != 0){
                imageUrl = LORENZO_PATH;
            }
            if (i==faithPath.getFaithPoints()){
                imageUrl = CROSS_PATH;
            }
            if(i==faithPath.getFaithPoints() && i == faithPath.getBlackCrossPosition()  && i != 0){
                imageUrl = CROSSES_PATH;
            }
            if(imageUrl!=null){
                tempCell.setStyle("-fx-background-image: url(" + imageUrl + ");" +
                        "-fx-background-size: contain;" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;");
            } else {
                tempCell.setStyle("-fx-background-image: null;");
            }
        }

    }
}
