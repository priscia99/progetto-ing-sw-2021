package it.polimi.ingsw.client.view.ui.gui.scene;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.network.auth_data.AuthData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class SceneController {
    private static Client client;
    public static void changeRootPane(){}
    public static void requestAuth(Stage primaryStage, Client c) {
            // Retrieving and storing the current client class object
            client = c;

            // Adding title to screen and setting padding to the border pane
            primaryStage.setTitle("Masters of Renaissance");
            BorderPane bp = new BorderPane();
            bp.setPadding(new Insets(10, 50, 50, 50));

            //Adding HBox
            HBox hb = new HBox();
            hb.setPadding(new Insets(20, 20, 20, 30));

            //Adding GridPane
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20, 20, 20, 20));
            gridPane.setHgap(5);
            gridPane.setVgap(5);

            //Implementing Nodes for GridPane
            Label nicknameLabel = new Label("Nickname");
            final TextField nicknameField = new TextField();
            Label lobbyLevel = new Label("Lobby ID");
            final TextField lobbyField = new TextField();
            Button joinButton = new Button("Join lobby");
            Label lobbyDimensionLabel = new Label("Number of players");
            final TextField lobbyDimensionField = new TextField();
            Button createButton = new Button("Create lobby");

            final Label lblMessage = new Label();

            //Adding Nodes to GridPane layout
            gridPane.add(nicknameLabel, 0, 0);
            gridPane.add(nicknameField, 1, 0);
            gridPane.add(lobbyLevel, 0, 1);
            gridPane.add(lobbyField, 1, 1);
            gridPane.add(lobbyDimensionLabel, 0, 2);
            gridPane.add(lobbyDimensionField, 1, 2);
            gridPane.add(joinButton, 2, 1);
            gridPane.add(createButton, 2, 2);
            gridPane.add(lblMessage, 3, 2);

            //Adding text and DropShadow effect to it
            Text text = new Text("Masterio delle Renascenze");
            text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

            //Adding text to HBox
            hb.getChildren().add(text);

            //Add ID's to Nodes
            bp.setId("bp");
            gridPane.setId("root");
            joinButton.setId("join-button");
            createButton.setId("create-button");
            text.setId("text");

            //Action for btnLogin
            joinButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Join lobby desire");
                    client.sendToSocket(AuthData.joinLobby(nicknameField.getText(), lobbyField.getText()));
                }
            });

            createButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    System.out.println("Create lobby desire");
                    client.sendToSocket(AuthData.createLobby(nicknameField.getText(), Integer.parseInt(lobbyDimensionField.getText())));
                }
            });

            //Add HBox and GridPane layout to BorderPane Layout
            bp.setTop(hb);
            bp.setCenter(gridPane);

            //Adding BorderPane to the scene and loading CSS
            Scene scene = new Scene(bp);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
    }

    public static void displayPopupMessage(Stage stage, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    public static void displayPopupError(Stage stage, String message){
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}
