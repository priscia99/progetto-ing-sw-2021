package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ProductionController extends GenericGUIController{

    private static final String DEV_CARDS_FRONT_PATH = "/img/cards/front/development-card-";

    private Pane productionPane;
    private ArrayList<ClientDevelopmentCard> developmentCards;
    private Map<String, Pane> productionCardsPanes;
    private ArrayList<ProductionEffect> leaderProductions;
    private Button confirmButton, cancelButton;

    public ProductionController(ClientController clientController, Pane productionPane, Map<String, Pane> productionCardsPanes, Button confirmButton, Button cancelButton) {
        super(clientController);
        this.productionPane = productionPane;
        this.productionCardsPanes = productionCardsPanes;
        this.confirmButton = confirmButton;
        this.cancelButton = cancelButton;
        this.confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedConfirmButton);
        this.cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCancelButton);
    }

    public void openProductionSelection(ArrayList<ProductionEffect> leaderProductions){
        this.leaderProductions = leaderProductions;
        productionPane.setVisible(true);
    }

    public void setAvailableDevelopments(ArrayList<ClientDevelopmentCard> developmentCards){
        this.developmentCards = developmentCards;
        for(int i=0; i < developmentCards.size(); i++){
            Pane tempCardPane = productionCardsPanes.get("dev-card-" + (i+1));
            tempCardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCard);
            String cardPath = DEV_CARDS_FRONT_PATH + developmentCards.get(i).getAssetLink() + ".png";
            tempCardPane.setStyle("-fx-background-image: url(" + cardPath + ");");
        }
    }

    private final EventHandler<MouseEvent> onClickedCard = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        int cardPosition = Integer.parseInt(triggeredPane.getId().split("-")[3]) - 1;
        if(triggeredPane.getEffect() != null){
            triggeredPane.setEffect(null);
        }else{
            triggeredPane.setEffect(new Glow(0.6));
        }
        SceneController.getMainGUIController().getPickResourcesFromStorageController().toggleProductionID(developmentCards.get(cardPosition).getId());
    };

    private final EventHandler<MouseEvent> onClickedConfirmButton = event -> {
        productionPane.setVisible(false);
        productionCardsPanes.entrySet().forEach(entry -> entry.getValue().setEffect(null));
        productionCardsPanes.entrySet().forEach(entry -> entry.getValue().removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCard));
        SceneController.getMainGUIController().getPickResourcesFromStorageController().chooseResourcesForProduction();
    };

    private final EventHandler<MouseEvent> onClickedCancelButton = event -> {
        productionPane.setVisible(false);
    };
}
