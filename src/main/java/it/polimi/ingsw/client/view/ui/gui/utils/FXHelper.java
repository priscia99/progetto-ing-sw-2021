package it.polimi.ingsw.client.view.ui.gui.utils;
import javafx.scene.Node;
import javafx.scene.effect.Glow;

public class FXHelper {

    public static void setBackground(Node target, String path){
        target.setStyle("-fx-background-image: url(" + path + ");");
    }

    public static void cleanBackground(Node target){
        target.setStyle("-fx-background-image: none;");
    }

    public static void cleanEffects(Node target){
        target.setEffect(null);
    }

    public static void highlight(Node target){
        target.setEffect(new Glow(0.8));
    }

}
