package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientCardsMarket;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
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

    private final GridPane developmentCardsMarketPane;
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

    /**
     * Refreshes the development cards market
     */
    public void refreshCardsMarket(){
        developmentCardsMap = new HashMap<>();
        for(int i = 0; i<4; i++){
            for(int j = 0; j < 3 ; j ++){
                int deckSize = cardsMarket.getDecks().get(i).get(j).size();
                Pane tempCardPane = (Pane) developmentCardsMarketPane.lookup("#card-" + (j+1) + "-" + (i+1));
                String cardImagePath;
                if(deckSize>0){
                    // there is a development card available for this cell -> display image
                    ClientDevelopmentCard tempCard = cardsMarket.getDecks().get(i).get(j).get(deckSize-1);
                    developmentCardsMap.put(tempCardPane, tempCard);
                    tempCardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedDevCard);
                    cardImagePath = AssetsHelper.getDevelopmentFrontPath(tempCard);
                } else {
                    // the current cell is empty -> display back card based on proper level and color of the cell
                    int cardPathNumber = 1 + j*4 + i;
                    cardImagePath = AssetsHelper.getBackDevelopmentPath(String.valueOf(cardPathNumber));
                }
                FXHelper.setBackground(tempCardPane, cardImagePath);
            }
        }
    }

    /**
     * Handler that is triggered when a development card is clicked from the market
     * This handler manage to show the selected development card zoom
     */
    private final EventHandler<javafx.scene.input.MouseEvent> onClickedDevCard = event -> {
        showCardZoom(developmentCardsMap.get((Pane)event.getSource()));
    };

    /**
     * Makes the development card zoom pane enabled and displays all possible actions
     * @param clientDevCard chosen development card
     */
    public void showCardZoom(ClientDevelopmentCard clientDevCard){
        this.selectedCard = clientDevCard;
        Button buyDevCardButton1 = (Button)((AnchorPane)devCardZoomGrid.getChildren().get(1)).getChildren().get(0);
        Button buyDevCardButton2 = (Button)((AnchorPane)devCardZoomGrid.getChildren().get(2)).getChildren().get(0);
        Button buyDevCardButton3 = (Button)((AnchorPane)devCardZoomGrid.getChildren().get(3)).getChildren().get(0);
        Button closeZoomButton = (Button)((AnchorPane)devCardZoomGrid.getChildren().get(4)).getChildren().get(0);

        buyDevCardButton1.addEventHandler(MouseEvent.MOUSE_CLICKED, onBuyCard1ButtonPressed);
        buyDevCardButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, onBuyCard2ButtonPressed);
        buyDevCardButton3.addEventHandler(MouseEvent.MOUSE_CLICKED, onBuyCard3ButtonPressed);
        closeZoomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onCloseZoomButtonPressed);

        FXHelper.setBackground(devCardZoomImage, AssetsHelper.getDevelopmentFrontPath(clientDevCard));
        devCardZoomPane.setVisible(true);
    }

    /**
     * Handler that is triggered when user wants to close the zoom pane
     * This handler disables the pane
     */
    private final EventHandler<MouseEvent> onCloseZoomButtonPressed = event -> {
        devCardZoomPane.setVisible(false);
    };

    /**
     * Handler that is triggered when user wants to buy the selected card and put it on first deck
     */
    private final EventHandler<MouseEvent> onBuyCard1ButtonPressed = event -> {
        SceneController.startMainAction();
        devCardZoomPane.setVisible(false);
        SceneController.getMainGUIController().getPickResourcesFromStorageController().enablePickResources(selectedCard.getId(), 0);
    };

    /**
     * Handler that is triggered when user wants to buy the selected card and put it on second deck
     */
    private final EventHandler<MouseEvent> onBuyCard2ButtonPressed = event -> {
        SceneController.startMainAction();
        devCardZoomPane.setVisible(false);
        SceneController.getMainGUIController().getPickResourcesFromStorageController().enablePickResources(selectedCard.getId(), 1);
    };

    /**
     * Handler that is triggered when user wants to buy the selected card and put it on third deck
     */
    private final EventHandler<MouseEvent> onBuyCard3ButtonPressed = event -> {
        SceneController.startMainAction();
        devCardZoomPane.setVisible(false);
        SceneController.getMainGUIController().getPickResourcesFromStorageController().enablePickResources(selectedCard.getId(), 2);
    };

    /**
     * Set the cards market
     * @param market game developmet cards market
     */
    public void setCardsMarket(ClientCardsMarket market){
        this.cardsMarket = market;
        this.refreshCardsMarket();
    }

}
