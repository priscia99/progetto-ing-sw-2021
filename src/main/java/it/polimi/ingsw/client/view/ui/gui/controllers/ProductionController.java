package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ResourceType;
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
    private static final String COIN_PATH = "/img/ico/coin.png";
    private static final String SERVANT_PATH = "/img/ico/servant.png";
    private static final String SHIELD_PATH = "/img/ico/shield.png";
    private static final String STONE_PATH = "/img/ico/stone.png";

    private Pane productionPane;
    private ArrayList<ClientDevelopmentCard> developmentCards;
    private Map<String, Pane> productionCardsPanes;
    private Map<String, Pane> genericProductionPanes;
    private Map<String, Pane> productionIcons;
    private ArrayList<ProductionEffect> leaderProductions;
    private Button confirmButton, cancelButton;
    private ResourceType selectedResourceType;

    public ProductionController(ClientController clientController, Pane productionPane, Map<String, Pane> productionCardsPanes, Map<String, Pane> genericProductionPanes, Map<String, Pane> productionIcons, Button confirmButton, Button cancelButton) {
        super(clientController);
        this.productionPane = productionPane;
        this.productionCardsPanes = productionCardsPanes;
        this.genericProductionPanes = genericProductionPanes;
        this.productionIcons = productionIcons;
        this.confirmButton = confirmButton;
        this.cancelButton = cancelButton;
        this.selectedResourceType = ResourceType.BLANK;
        this.confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedConfirmButton);
        this.cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCancelButton);
    }

    public void openProductionSelection(ArrayList<ProductionEffect> leaderProductions){
        this.leaderProductions = leaderProductions;
        this.productionIcons.entrySet().stream().forEach(entry -> entry.getValue().addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceIcon));
        this.genericProductionPanes.entrySet().stream().forEach(entry -> entry.getValue().addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedGenericPosition));
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

    private final EventHandler<MouseEvent> onClickedGenericPosition = event -> {
        Pane selectedPosition = (Pane) event.getSource();
        switch (selectedResourceType){
            case COIN -> selectedPosition.setStyle("-fx-background-image: url(" + COIN_PATH + ");");
            case SERVANT -> selectedPosition.setStyle("-fx-background-image: url(" + SERVANT_PATH + ");");
            case SHIELD -> selectedPosition.setStyle("-fx-background-image: url(" + SHIELD_PATH + ");");
            case STONE -> selectedPosition.setStyle("-fx-background-image: url(" + STONE_PATH + ");");
        }
    };

    private final EventHandler<MouseEvent> onClickedResourceIcon = event -> {
        Pane selectedIcon = (Pane) event.getSource();
        switch (selectedIcon.getId()){
            case "coin-icon" -> selectedResourceType = ResourceType.COIN;
            case "servant-icon" -> selectedResourceType = ResourceType.SERVANT;
            case "shield-icon" -> selectedResourceType = ResourceType.SHIELD;
            case "stone-icon" -> selectedResourceType = ResourceType.STONE;
        }
        this.productionIcons.entrySet().stream().forEach(entry -> entry.getValue().setEffect(null));
        selectedIcon.setEffect(new Glow(0.6));
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
