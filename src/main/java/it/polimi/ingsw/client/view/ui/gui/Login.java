package it.polimi.ingsw.client.view.ui.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Masters of Renaissance");
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10,50,50, 50));

        // Adding HBOX
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20,20,20,20));

        // Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // adding labels and fields
        Label nicknameLabel = new Label("Nickname");
        final TextField nicknameText = new TextField();
        Label lobbyLabel = new Label("Lobby ID");
        final TextField lobbyText = new TextField();
        Button joinButton = new Button("Login");
        final Label labelMessage = new Label();

        gridPane.add(nicknameLabel, 0, 0);
        gridPane.add(lobbyLabel, 1, 0);
        gridPane.add(nicknameText, 0, 1);
        gridPane.add(lobbyText, 1, 1);
        gridPane.add(joinButton, 2,1);
        gridPane.add(labelMessage, 1,2);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();

    }
}
