package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientDevelopmentCard;
import it.polimi.ingsw.client.model.ClientLeaderCard;
import it.polimi.ingsw.client.view.ui.gui.scene.SceneController;
import it.polimi.ingsw.client.view.ui.gui.utils.AssetsHelper;
import it.polimi.ingsw.client.view.ui.gui.utils.FXHelper;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductionController extends GenericGUIController{

    private Pane productionPane;
    private ArrayList<ClientDevelopmentCard> developmentCards;
    private Map<String, Pane> productionCardsPanes;
    private Map<String, Pane> genericProductionPanes;
    private Map<String, Pane> productionIcons;
    private ArrayList<ClientLeaderCard> leaderCards;
    private Button confirmButton, cancelButton;
    private ResourceType selectedResourceType;
    private Map<String, ResourceType> chosenGenericProduction;

    public ProductionController(ClientController clientController, Pane productionPane, Map<String, Pane> productionCardsPanes, Map<String, Pane> genericProductionPanes, Map<String, Pane> productionIcons, Button confirmButton, Button cancelButton) {
        super(clientController);
        this.productionPane = productionPane;
        this.productionCardsPanes = productionCardsPanes;
        this.genericProductionPanes = genericProductionPanes;
        this.productionIcons = productionIcons;
        this.confirmButton = confirmButton;
        this.cancelButton = cancelButton;
        this.selectedResourceType = ResourceType.BLANK;
        this.chosenGenericProduction = new HashMap<>();
        this.confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedConfirmButton);
        this.cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCancelButton);
    }

    /**
     * Open the production selection pane
     * @param leaderProductions list of leader cards with production effects
     */
    public void openProductionSelection(ArrayList<ClientLeaderCard> leaderProductions){
        this.leaderCards = leaderProductions;
        this.productionIcons.entrySet().stream().forEach(entry -> entry.getValue().addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedResourceIcon));
        this.genericProductionPanes.entrySet().stream().forEach(entry -> entry.getValue().addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedGenericPosition));
        if(leaderCards.size()<2){
            genericProductionPanes.get("generic-leader-2").removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedGenericPosition);
        }
        if(leaderCards.size()<1){
            genericProductionPanes.get("generic-leader-1").removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedGenericPosition);
        }
        for(int i=0; i<this.leaderCards.size(); i++){
            Pane tempCardPane = productionCardsPanes.get("leader-card-" + (i+1));
            FXHelper.setBackground(tempCardPane, AssetsHelper.getLeaderFrontPath(leaderCards.get(i)));
        }
        productionPane.setVisible(true);

    }

    /**
     * Set the available development cards usable for a production action
     * @param developmentCards list of development cards usable for a production action
     */
    public void setAvailableDevelopments(ArrayList<ClientDevelopmentCard> developmentCards){
        this.developmentCards = developmentCards;
        for(int i=0; i < developmentCards.size(); i++){
            Pane tempCardPane = productionCardsPanes.get("dev-card-" + (i+1));
            tempCardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCard);
            FXHelper.setBackground(tempCardPane, AssetsHelper.getDevelopmentFrontPath(developmentCards.get(i)));
        }
    }

    /**
     * Handler that is triggered when a development card is selected for the production action
     * This handler adds the selected card to the list of ones used for the production action
     */
    private final EventHandler<MouseEvent> onClickedCard = event -> {
        Pane triggeredPane = (Pane) event.getSource();
        int cardPosition = Integer.parseInt(triggeredPane.getId().split("-")[3]) - 1;
        if(triggeredPane.getEffect() != null){
            FXHelper.cleanEffects(triggeredPane);
        }else{
            FXHelper.highlight(triggeredPane);
        }
        SceneController.getMainGUIController().getPickResourcesFromStorageController().toggleProductionID(developmentCards.get(cardPosition).getId());
    };

    /**
     * Handler that is triggered when a generic production pane is selected
     * This handler creates the generic production effect and refreshes the background
     */
    private final EventHandler<MouseEvent> onClickedGenericPosition = event -> {
        Pane selectedPosition = (Pane) event.getSource();
        chosenGenericProduction.put(selectedPosition.getId(), selectedResourceType);
        FXHelper.setBackground(selectedPosition, AssetsHelper.getResourceIconPath(selectedResourceType));
    };

    /**
     * Handler that is triggered when a resource icon is selected
     * This handler selects the proper resource as the one used for a generic production or a leader card production
     */
    private final EventHandler<MouseEvent> onClickedResourceIcon = event -> {
        Pane selectedIcon = (Pane) event.getSource();
        switch (selectedIcon.getId()){
            case "coin-icon" -> selectedResourceType = ResourceType.COIN;
            case "servant-icon" -> selectedResourceType = ResourceType.SERVANT;
            case "shield-icon" -> selectedResourceType = ResourceType.SHIELD;
            case "stone-icon" -> selectedResourceType = ResourceType.STONE;
        }
        this.productionIcons.values().forEach(FXHelper::cleanEffects);
        FXHelper.highlight(selectedIcon);
    };

    /**
     * Handler that is triggered when the user wants to confirm his productions choice
     * The handler tells to another controller that the player has now to choose the resources
     */
    private final EventHandler<MouseEvent> onClickedConfirmButton = event -> {
        productionPane.setVisible(false);
        productionCardsPanes.values().forEach(FXHelper::cleanEffects);
        productionCardsPanes.entrySet().forEach(entry -> entry.getValue().removeEventHandler(MouseEvent.MOUSE_CLICKED, onClickedCard));
        productionCardsPanes.values().forEach(FXHelper::cleanBackground);
        Optional<ProductionEffect> genericProductionEffect = Optional.empty();
        if(chosenGenericProduction.get("generic-input-1") != null &&
                chosenGenericProduction.get("generic-input-2") != null &&
                chosenGenericProduction.get("generic-output") != null) {
            ArrayList<ResourceStock> inStocks = new ArrayList<>();
            ArrayList<ResourceStock> outStocks = new ArrayList<>();
            inStocks.add(new ResourceStock(chosenGenericProduction.get("generic-input-1"), 1));
            inStocks.add(new ResourceStock(chosenGenericProduction.get("generic-input-2"), 1));
            outStocks.add(new ResourceStock(chosenGenericProduction.get("generic-output"), 1));
            genericProductionEffect = Optional.of(new ProductionEffect(inStocks, outStocks));
            SceneController.getMainGUIController().getPickResourcesFromStorageController().setGenericProduction(genericProductionEffect);
        }

        ArrayList<ProductionEffect> leaderProductions = new ArrayList<>();

        if(chosenGenericProduction.get("generic-leader-1") != null){
            ResourceType convertedType = chosenGenericProduction.get("generic-leader-1");
            ArrayList<ResourceStock> inStock = ((ProductionEffect)leaderCards.get(0).getEffect()).getInStocks();
            ArrayList<ResourceStock> outStock = ((ProductionEffect)leaderCards.get(0).getEffect()).getOutStockConverted(convertedType);
            ProductionEffect firstLeaderProduction = new ProductionEffect(inStock,outStock);
            leaderProductions.add(firstLeaderProduction);
        }

        if(chosenGenericProduction.get("generic-leader-2") != null){
            ResourceType convertedType = chosenGenericProduction.get("generic-leader-2");
            ArrayList<ResourceStock> inStock = ((ProductionEffect)leaderCards.get(1).getEffect()).getInStocks();
            ArrayList<ResourceStock> outStock = ((ProductionEffect)leaderCards.get(1).getEffect()).getOutStockConverted(convertedType);
            ProductionEffect firstLeaderProduction = new ProductionEffect(inStock,outStock);
            leaderProductions.add(firstLeaderProduction);
        }

        SceneController.getMainGUIController().getPickResourcesFromStorageController().setLeaderProductions(leaderProductions);
        SceneController.getMainGUIController().getPickResourcesFromStorageController().chooseResourcesForProduction();
        chosenGenericProduction.clear();
    };

    /**
     * Handler that is triggered when an user wants to abort a production action
     * This handler sets the production screen not visible
     */
    private final EventHandler<MouseEvent> onClickedCancelButton = event -> {
        productionPane.setVisible(false);
        SceneController.endMainAction();
    };

    /**
     * Clear all icons backgrounds in production screen
     */
    public void cleanIcons(){
        for(Pane genericPane : genericProductionPanes.values()){
            FXHelper.cleanBackground(genericPane);
        }
    }
}
