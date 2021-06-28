package it.polimi.ingsw.client.view.ui.gui.utils;
import javafx.scene.Node;

public class FXHelper {

    public static void setBackground(Node target, String path){
        target.setStyle("-fx-background-image: url(" + path + ");");
    }
}
