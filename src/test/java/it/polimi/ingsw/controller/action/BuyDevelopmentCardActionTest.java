package it.polimi.ingsw.controller.action;


import it.polimi.ingsw.network.message.from_client.BuyDevelopmentCardMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.testUtils.MockProvider;
import it.polimi.ingsw.testUtils.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BuyDevelopmentCardActionTest {
    private Game game;
    private ServerController controller;
    @BeforeEach
    public void setUp(){
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player has new card")
    public void testPlayerHasNewCard(){
        assertEquals(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        ResourceRequirement requirement = (ResourceRequirement) game.getCardMarket().getCard(0,0).getRequirement();
        HashMap<ResourcePosition, ResourceStock> toConsume = new HashMap<>();
        requirement.getResourceStocks().forEach(stock-> toConsume.put(ResourcePosition.STRONG_BOX, stock));
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage("1" , 1, toConsume);
        controller.tryAction(()->action.execute(controller));
        assertEquals(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 1);
    }

    @Test
    @DisplayName("Test player tries to buy a wrong card")
    public void testPlayerBuyWrongCard(){
        DevelopmentCard cardToBuy = game.getCardMarket().getDecks()[0][0].get(3);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        ResourceRequirement requirement = (ResourceRequirement) game.getCardMarket().getCard(0,0).getRequirement();
        HashMap<ResourcePosition, ResourceStock> toConsume = new HashMap<>();
        requirement.getResourceStocks().forEach(stock-> toConsume.put(ResourcePosition.STRONG_BOX, stock));
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage("1",1, toConsume);
        Exception exception = assertThrows(Exception.class, () -> {
            action.execute(controller);
        });
        String expectedMessage = "The requested card is not available in the market";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test player buy new card")
    public void testPlayerGetBoughtCard(){
        // FIXME Fix HashMap toResource -> cannot add multiple resource stock for the same STRONGBOX!!
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,0);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        ResourceRequirement requirement = (ResourceRequirement) cardToBuy.getRequirement();
        HashMap<ResourcePosition, ResourceStock> toConsume = new HashMap<>();
        requirement.getResourceStocks().forEach(stock-> toConsume.put(ResourcePosition.STRONG_BOX, stock));
        for (ResourceStock stock : requirement.getResourceStocks()) {
            toConsume.put(ResourcePosition.STRONG_BOX, stock);
        }
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage(cardToBuy.getId(),1, toConsume);
        try {
            action.execute(controller);
        }catch (Exception e){
            e.printStackTrace();
        }
        assertEquals(1, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[1].getCardNumber());
    }

    @Test
    @DisplayName("Test cards market renew")
    public void testCardsMaketRenew(){
        DevelopmentCard cardToBuy = game.getCardMarket().getDecks()[0][0].get(3);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        ResourceRequirement requirement = (ResourceRequirement) game.getCardMarket().getCard(0,0).getRequirement();
        HashMap<ResourcePosition, ResourceStock> toConsume = new HashMap<>();
        requirement.getResourceStocks().forEach(stock-> toConsume.put(ResourcePosition.STRONG_BOX, stock));
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage("1",1, toConsume);
        controller.tryAction(()->action.execute(controller));
        assertEquals(game.getCardMarket().getDecks()[0][0].size(), 3);
        assertTrue(game.getCardMarket().getDecks()[0][0].get(2) != cardToBuy);
    }
}
