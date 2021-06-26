package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientCardsMarket;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

public class DevelopmentCardMarketController extends GenericGUIController{

    private static final String DEV_CARDS_FRONT_PATH = "/img/cards/front/development-card-";
    private static final String DEV_CARDS_BACK_PATH = "/img/cards/back/dev_back_";

    private final GridPane developmentCardsMarketPane;
    private ObservableList<Node> marketCards;
    private ClientCardsMarket cardsMarket;
    private Pane devCardZoomPane;
    private GridPane devCardZoomGrid;
    private Map<Pane, ClientDevelopmentCard> developmentCardsMap;
    private ClientDevelopmentCard selectedCard = null;
    private Pane devCardZoomImage;

    public DevelopmentCardMarketController(ClientController clientController, GridPane developmentCardsMarketPane, Pane devCardZoomPane, GridPane devCardZoomGrid, Pane devCardZoomImage) {
        super(clientController);
        this.developmentCardsMarketPane = developmentCardsMarketPane;
        this.devCardZoomPane = devCardZoomPane;
        this.devCardZoomGrid = devCardZoomGrid;
        this.devCardZoomImage = devCardZoomImage;
    }

    public void refreshCardsMarket(){
        String cardImagePath = null;
        Image cardImage = null;
        BackgroundImage bgImg = null;
        developmentCardsMap = new HashMap<>();
        int index = 0;
        for(int i = 0; i<4; i++){
            for(int j = 0; j < 3 ; j ++){
                int deckSize = cardsMarket.getDecks().get(i).get(j).size();
                Pane tempCardPane = (Pane) developmentCardsMarketPane.lookup("#card-" + String.valueOf(j+1) + "-" + String.valueOf(i+1));
                if(deckSize>0){
                    // there is a development card available for this cell -> display image
                    ClientDevelopmentCard tempCard = cardsMarket.getDecks().get(i).get(j).get(deckSize-1);
                    developmentCardsMap.put(tempCardPane, tempCard);
                    tempCardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedDevCard);
                    cardImagePath = DEV_CARDS_FRONT_PATH + tempCard.getAssetLink() + ".png";
                } else {
                    // the current cell is empty -> display back card based on proper level and color of the cell
                    int cardPathNumber = 1 + j*4 + i;
                    cardImagePath = DEV_CARDS_BACK_PATH + cardPathNumber + ".png";
                }
                tempCardPane.setStyle("-fx-background-image: url(" + cardImagePath + ");");
                index++;
            }
        }
    }

    private final EventHandler<javafx.scene.input.MouseEvent> onClickedDevCard = event -> {
        showCardZoom(developmentCardsMap.get((Pane)event.getSource()));
    };

    public void showCardZoom(ClientDevelopmentCard clientDevCard){
        this.selectedCard = clientDevCard;
        String cardAssetPath = clientDevCard.getAssetLink();
        Button buyDevCardButton = (Button)((AnchorPane)devCardZoomGrid.getChildren().get(1)).getChildren().get(0);
        Button closeZoomButton = (Button)((AnchorPane)devCardZoomGrid.getChildren().get(2)).getChildren().get(0);

        buyDevCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onBuyCardButtonPressed);
        closeZoomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onCloseZoomButtonPressed);

        String cardImagePath = DEV_CARDS_FRONT_PATH + cardAssetPath + ".png";
        devCardZoomImage.setStyle("-fx-background-image: url(" + cardImagePath + ");");
        System.out.println("Set as visible! ");
        devCardZoomPane.setVisible(true);
    }

    private final EventHandler<MouseEvent> onCloseZoomButtonPressed = event -> {
        devCardZoomPane.setVisible(false);
    };

    private final EventHandler<MouseEvent> onBuyCardButtonPressed = event -> {
        devCardZoomPane.setVisible(false);
        SceneController.getMainGUIController().getPickResourcesFromStorageController().enablePickResources(selectedCard.getId(), 0);
    };

    public void setCardsMarket(ClientCardsMarket market){
        this.cardsMarket = market;
        this.refreshCardsMarket();
    }

}
