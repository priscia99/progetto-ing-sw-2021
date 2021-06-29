package it.polimi.ingsw.client.view.ui.gui.scene;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.ui.gui.controllers.MainGUIController;
import it.polimi.ingsw.network.auth_data.AuthData;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SceneController {
    private static Client client;
    private static MainGUIController mainGUIController;

    public static void changeRootPane(){}
    public static void requestAuth(Stage primaryStage, Client c) {
            // Retrieving and storing the current client class object
            client = c;

            // Adding title to screen and setting padding to the border pane
            primaryStage.setTitle("Masters of Renaissance");
            BorderPane bp = new BorderPane();
            Scene loginScene = new Scene(bp);
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
            nicknameField.setMinWidth(200);
            Label lobbyLevel = new Label("Lobby ID");
            final TextField lobbyField = new TextField();
            lobbyField.setMinWidth(200);
            Button joinButton = new Button("Join lobby");
            Label lobbyDimensionLabel = new Label("Number of players");
            final TextField lobbyDimensionField = new TextField();
            lobbyDimensionField.setMinWidth(200);
            Button createButton = new Button("Create lobby");

            final TextField loginMessage = new TextField();
            loginMessage.setId("login-message");
            loginMessage.setText("");
            loginMessage.setEditable(false);
            loginMessage.setMinWidth(200);
            loginMessage.setBackground(Background.EMPTY);

            //Adding Nodes to GridPane layout
            gridPane.add(nicknameLabel, 0, 0);
            gridPane.add(nicknameField, 1, 0);
            gridPane.add(lobbyLevel, 0, 1);
            gridPane.add(lobbyField, 1, 1);
            gridPane.add(lobbyDimensionLabel, 0, 2);
            gridPane.add(lobbyDimensionField, 1, 2);
            gridPane.add(joinButton, 2, 1);
            gridPane.add(createButton, 2, 2);
            gridPane.add(loginMessage, 1, 3);

            //Adding text and DropShadow effect to it
            Text text = new Text("Masters of Renaissance");
            text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28));

            //Adding text to HBox
            hb.getChildren().add(text);

            //Add ID's to Nodes
            bp.setId("bp");
            nicknameField.setId("nickname-field");
            lobbyField.setId("lobby-field");
            lobbyDimensionField.setId("lobby-dimension-field");
            gridPane.setId("root");
            joinButton.setId("join-button");
            createButton.setId("create-button");
            text.setId("text");

            //Action for btnLogin
            joinButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(!checkParameters(loginScene, 0)){
                        loginMessage.setText("Some parameters are not correct!");
                    }
                    else{
                        client.setMyUsername(nicknameField.getText());
                        client.sendToSocket(AuthData.joinLobby(nicknameField.getText(), lobbyField.getText()));
                    }
                }
            });

            createButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    if(!checkParameters(loginScene, 1)){
                        loginMessage.setText("Some parameters are not correct!");
                    }
                    else{
                        client.setMyUsername(nicknameField.getText());
                        client.sendToSocket(AuthData.createLobby(nicknameField.getText(), Integer.parseInt(lobbyDimensionField.getText())));
                    }
                }
            });

            //Add HBox and GridPane layout to BorderPane Layout
            bp.setTop(hb);
            bp.setCenter(gridPane);

            //Adding BorderPane to the scene and loading CSS
            primaryStage.setScene(loginScene);
            primaryStage.setResizable(false);
            primaryStage.show();
    }

    public static void showGameScene(Stage primaryStage){

        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/home.fxml"));
        Parent root = null;
        try{
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainGUIController = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1080);
        primaryStage.setTitle("Masters of Renaissance");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void displayPopupMessage(Stage stage, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    public static void displayPopupError(Stage stage, String message){
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private static boolean checkParameters(Scene scene, int authType){
        TextField nickname = (TextField) scene.lookup("#nickname-field");
        TextField loginInfo = null;
        if(authType > 1) {return false;}    // Invalid authentication type
        if(nickname.getText() == null || nickname.getText().isEmpty()) {return false;}  // Checking nickname
        if(authType == 0){
            // Validating join lobby parameters
            loginInfo = (TextField) scene.lookup("#lobby-field");
            if(loginInfo.getText() == null || loginInfo.getText().isEmpty()) {return false;}
        }
        else if(authType == 1){
            // Validating create lobby parameters
            loginInfo = (TextField) scene.lookup("#lobby-dimension-field");
            try {
                Integer.parseInt(loginInfo.getText());
            } catch(NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static MainGUIController getMainGUIController() {
        return mainGUIController;
    }

    public static void startMainAction(){
        mainGUIController.enableMainActionState(client.getMyUsername());
    }

    public static void endMainAction(){
        mainGUIController.disableMainActionState();
    }

    public static void displayEndGame(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game is ended!");
        alert.setResizable(false);
        alert.setContentText("Press OK to exit this game.");

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            System.exit(0);
        }
    }


}
