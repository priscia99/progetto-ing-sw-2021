package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXClient extends Application {
    private static String ip;
    /**
     * Starts the GUI, initialize the client which tries to connect to the server
     * @param primaryStage GUI primary stage
     * @throws Exception generic exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Client client = new Client(ip, 5000, new GUI(primaryStage));
        new Thread(() -> {
        try{
                client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }}).start();
        primaryStage.setTitle("JavaFX Welcome");
        primaryStage.show();
    }

    public static void setIp(String ip){
        JavaFXClient.ip = ip;
    }
}
