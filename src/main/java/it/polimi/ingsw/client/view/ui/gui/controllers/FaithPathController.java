package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientFaithPath;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Objects;

public class FaithPathController extends GenericGUIController {
    private static int CELLS_NUMBER = 24;                           // number of faith path cells


    private final ArrayList<Pane> popeFavors;
    GridPane faithPathPane;                                         // faith path generic pane
    ObservableList<Node> cellsPaneList;                             // list of position panes

    public FaithPathController(ClientController clientController, GridPane faithPathPane, Pane popeFavor1, Pane popeFavor2, Pane popeFavor3) {
        super(clientController);
        this.faithPathPane = faithPathPane;
        this.cellsPaneList = faithPathPane.getChildren();
        this.popeFavors = new ArrayList<>();
        popeFavors.add(popeFavor1);
        popeFavors.add(popeFavor2);
        popeFavors.add(popeFavor3);
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
                imageUrl = AssetsHelper.getLorenzoPath();
            }
            if (i==faithPath.getFaithPoints()){
                imageUrl = AssetsHelper.getCrossPath();
            }
            if(i==faithPath.getFaithPoints() && i == faithPath.getBlackCrossPosition()  && i != 0){
                imageUrl = AssetsHelper.getCrossesPath();
            }
            if(imageUrl!=null){
                FXHelper.setCenteredBackground(tempCell, imageUrl);
            } else {
                FXHelper.cleanBackground(tempCell);
            }
        }

        for(int i = 0; i<3; i++){
            String path = AssetsHelper.getPopeFavourPath(i+1, faithPath.getPopeFavor(i));
            FXHelper.setBackground(popeFavors.get(i), path);
        }

    }
}
