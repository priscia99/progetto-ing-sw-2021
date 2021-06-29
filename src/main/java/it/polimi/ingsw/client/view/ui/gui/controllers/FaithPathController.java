package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientFaithPath;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
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
    private static final String FAVOR_1_PATH = "/img/favor-1-inactive.png";
    private static final String FAVOR_2_PATH = "/img/favor-2-inactive.png";
    private static final String FAVOR_3_PATH = "/img/favor-3-inactive.png";

    private final Pane popeFavor1, popeFavor2, popeFavor3;
    GridPane faithPathPane;                                         // faith path generic pane
    ObservableList<Node> cellsPaneList;                             // list of position panes

    public FaithPathController(ClientController clientController, GridPane faithPathPane, Pane popeFavor1, Pane popeFavor2, Pane popeFavor3) {
        super(clientController);
        this.faithPathPane = faithPathPane;
        this.cellsPaneList = faithPathPane.getChildren();
        this.popeFavor1 = popeFavor1;
        this.popeFavor2 = popeFavor2;
        this.popeFavor3 = popeFavor3;
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


        FXHelper.setBackground(popeFavor1, FAVOR_1_PATH);
        if(faithPath.getPopeFavor(0)){
            FXHelper.highlight(popeFavor1);
        }

        FXHelper.setBackground(popeFavor2, FAVOR_2_PATH);
        if(faithPath.getPopeFavor(1)){
            FXHelper.highlight(popeFavor2);
        }

        FXHelper.setBackground(popeFavor3, FAVOR_3_PATH);
        if(faithPath.getPopeFavor(2)){
            FXHelper.highlight(popeFavor3);
        }

    }
}
