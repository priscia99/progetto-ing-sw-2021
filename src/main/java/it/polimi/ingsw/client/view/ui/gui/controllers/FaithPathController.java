package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.model.ClientFaithPath;
import it.polimi.ingsw.client.view.FaithPathView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Objects;

public class FaithPathController {
    private static final String CROSS_PATH = "/img/croce.png";      // cross image path
    GridPane faithPathPane;                                         // faith path generic pane
    ObservableList<Node> cellsPaneList;                             // list of position panes

    public FaithPathController(GridPane faithPathPane) {
        this.faithPathPane = faithPathPane;
        this.cellsPaneList = faithPathPane.getChildren();
    }

    /**
     * Refreshes faith path and display cross position based on current faith points
     * @param faithPath Player's faith path
     */
    public void refreshFaithPath(ClientFaithPath faithPath){
        Pane activeCell = (Pane) cellsPaneList.get(faithPath.getFaithPoints());
        Image crossImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(CROSS_PATH + ".png")));
        BackgroundImage bgImg = new BackgroundImage(crossImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        activeCell.setBackground(new Background(bgImg));
    }
}
