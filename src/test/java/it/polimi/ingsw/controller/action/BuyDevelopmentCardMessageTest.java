package it.polimi.ingsw.controller.action;


import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.from_client.BuyDevelopmentCardMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.requirement.Requirement;
import it.polimi.ingsw.server.model.card.requirement.ResourceRequirement;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ConsumeTarget;
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

public class BuyDevelopmentCardMessageTest {
    private Game game;
    private ServerController controller;
    @BeforeEach
    public void setUp() throws GameException {
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player buy successfully a development card")
    public void testPlayerBuyDevelopmentCardSuccess() throws GameException {
        // Check if player has no development cards inside his personal decks
        assertEquals(0, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        // Make the player rich giving him some resources
        TestHelper.makeHimRich(game.getCurrentPlayer());
        // Randomly try to buy a development card from whose available in the market
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,0);
        // Check if development cards market is filled correctly and selected card is not removed yet
        assertEquals(4, game.getCardMarket().getDecks()[0][0].size());
        assertTrue(game.getCardMarket().getCard(0,0).getId() == cardToBuy.getId());
        ResourceRequirement cardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        ConsumeTarget consumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : cardRequirement.getResourceStocks()){
                consumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message giving valid parameters
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, consumeTarget);
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });
        // Check if card is inserted into proper player deck
        assertEquals(1, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getCardNumber());
        assertEquals(cardToBuy.getId(), game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getTopCard().getId());
        // Check if development cards market is renewed
        assertEquals(3, game.getCardMarket().getDecks()[0][0].size());
        assertFalse(game.getCardMarket().getCard(0,0).getId() == cardToBuy.getId());
    }

    @Test
    @DisplayName("Test player tries to buy an invalid card")
    public void testPlayerBuyDevelopmentCardNotValid(){
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage("D99", 0, new ConsumeTarget());
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "The requested card is not available in the market";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Test player tries to buy a valid card without giving resources")
    public void testPlayerBuyDevelopmentCardWithoutResources() throws GameException {
        // Make the player rich giving him some resources
        TestHelper.makeHimRich(game.getCurrentPlayer());
        // Randomly try to buy a development card from whose available in the market
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,0);
        ResourceRequirement cardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        // Create the proper without giving correct target consume
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, new ConsumeTarget());
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "Wrong resources to buy card!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Test player tries to buy a valid card without having resources")
    public void testPlayerBuyDevelopmentCardHavingNothing(){
        // Randomly try to buy a development card from whose available in the market
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,0);
        ResourceRequirement cardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        ConsumeTarget consumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : cardRequirement.getResourceStocks()){
                consumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, consumeTarget);
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "You do not own resources selected!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Test add a development card with a wrong level")
    public void testPlayerAddDevelopmentCardWrongLevel() throws GameException {
        // Check if player has no development cards inside his personal decks
        assertEquals(0, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        // Make the player rich giving him some resources
        TestHelper.makeHimRich(game.getCurrentPlayer());
        // Randomly try to buy a development card from whose available in the market
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,1);
        ResourceRequirement cardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        ConsumeTarget consumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : cardRequirement.getResourceStocks()){
                consumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, consumeTarget);
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "You can't add development card to selected deck!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Test add successfully a development card with an higher level")
    public void testPlayerAddDevelopmentCardHigherLevel() throws GameException {
        // FIRST PLAYER
        Player testPlayer = game.getCurrentPlayer();
        // Check if player has no development cards inside his personal decks
        assertEquals(0, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        // Make the player rich giving him some resources
        TestHelper.makeHimRich(game.getCurrentPlayer());
        // Randomly try to buy a development card from whose available in the market
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,0);
        final ResourceRequirement cardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        final ConsumeTarget consumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : cardRequirement.getResourceStocks()){
                consumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message giving valid parameters
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, consumeTarget);
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });
        // Check if card is inserted into proper player deck
        assertEquals(1, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getCardNumber());
        assertEquals(cardToBuy.getId(), game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getTopCard().getId());
        // Iterating through turns until it's testPlayer's turn again
        do {
            try {
                game.nextTurn();
            } catch (GameException e) {
                e.printStackTrace();
            }
        }while(!testPlayer.getNickname().equals(game.getCurrentPlayer().getNickname()));
        cardToBuy = game.getCardMarket().getCard(0,1);  // Same color, higher level
        // Make the player rich giving him some resources again
        TestHelper.makeHimRich(game.getCurrentPlayer());
        final ResourceRequirement secondCardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        final ConsumeTarget secondConsumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : secondCardRequirement.getResourceStocks()){
                secondConsumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message giving valid parameters
        BuyDevelopmentCardMessage secondMessage = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, secondConsumeTarget);
        assertDoesNotThrow(() -> {
            secondMessage.execute(controller);
        });
        assertEquals(2, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getCardNumber());
        assertEquals(cardToBuy.getId(), game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getTopCard().getId());
    }

    @Test
    @DisplayName("Test player tries to buy another card in the same turn")
    public void testPlayerBuyAnotherCardInSameTurn() throws GameException {
        // FIRST TRY TO BUY A DEVELOPMENT CARD -> SUCCESS
        Player testPlayer = game.getCurrentPlayer();
        // Check if player has no development cards inside his personal decks
        assertEquals(0, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        // Make the player rich giving him some resources
        TestHelper.makeHimRich(game.getCurrentPlayer());
        // Randomly try to buy a development card from whose available in the market
        DevelopmentCard cardToBuy = game.getCardMarket().getCard(0,0);
        final ResourceRequirement cardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        final ConsumeTarget consumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : cardRequirement.getResourceStocks()){
                consumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message giving valid parameters
        BuyDevelopmentCardMessage message = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, consumeTarget);
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });
        // Check if card is inserted into proper player deck
        assertEquals(1, game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getCardNumber());
        assertEquals(cardToBuy.getId(), game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[0].getTopCard().getId());

        // SECOND TRY TU BUY A NEW DEVELOPMENT CARD IN SAME ACTION -> SHOULD FAIL
        cardToBuy = game.getCardMarket().getCard(0,1);  // Same color, higher level
        // Make the player rich giving him some resources again
        TestHelper.makeHimRich(game.getCurrentPlayer());
        final ResourceRequirement secondCardRequirement = (ResourceRequirement) cardToBuy.getRequirement();   // retrieve resource requirements for that card
        final ConsumeTarget secondConsumeTarget = new ConsumeTarget();  // Create consume target
        // Iterate through the required resource stocks and create a proper consume target object
        assertDoesNotThrow(() ->{
            for(ResourceStock stock : secondCardRequirement.getResourceStocks()){
                secondConsumeTarget.put(ResourcePosition.STRONG_BOX, stock);
            }
        });
        // Create the proper message giving valid parameters
        BuyDevelopmentCardMessage secondMessage = new BuyDevelopmentCardMessage(cardToBuy.getId(), 0, secondConsumeTarget);
        Exception exception = assertThrows(Exception.class, () -> {
            secondMessage.execute(controller);
        });
        String expectedMessage = "You have already done main action this turn!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
