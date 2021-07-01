package it.polimi.ingsw.client.view.ui.gui.utils;
import javafx.scene.Node;
import javafx.scene.effect.Glow;

public class FXHelper {

    /**
     * Sets the background for the selected target
     * @param target target which background needs to be updated
     * @param path background image path
     */
    public static void setBackground(Node target, String path){
        target.setStyle("-fx-background-image: url(" + path + ");");
    }

    public static void setCenteredBackground(Node target, String path){
        target.setStyle("-fx-background-image: url(" + path + ");" +
                "-fx-background-size: contain;" +
                "-fx-background-position: center;" +
                "-fx-background-repeat: no-repeat;");
    }

    /**
     * Clear all backgrounds for a selected target
     * @param target target which background needs to be removed
     */
    public static void cleanBackground(Node target){
        target.setStyle("-fx-background-image: none;");
    }

    /**
     * Clean all effects for a selected target
     * @param target target which JAVAFX effects needs to be removed
     */
    public static void cleanEffects(Node target){
        target.setEffect(null);
    }

    /**
     * Highlights effect for a selected target
     * @param target target which needs to be highlighted
     */
    public static void highlight(Node target){
        target.setEffect(new Glow(0.8));
    }

}
