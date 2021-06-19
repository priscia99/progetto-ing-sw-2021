package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        Client client = new Client("127.0.0.1", 12345, new GUI(primaryStage));
        new Thread(() -> {
        try{
                client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }}).start();
        primaryStage.setTitle("JavaFX Welcome");
        primaryStage.show();
    }
}
