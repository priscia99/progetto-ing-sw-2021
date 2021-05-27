package it.polimi.ingsw.controller.action;


import it.polimi.ingsw.network.message.from_client.BuyDevelopmentCardMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.testUtils.MockProvider;
import it.polimi.ingsw.testUtils.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage(0,0,1);
        action.execute(controller);
        Assertions.assertEquals(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 1);
    }

    @Test
    @DisplayName("Test player get bought card")
    public void testPlayerGetBoughtCard(){
        DevelopmentCard cardToBuy = game.getCardMarket().getDecks()[0][0].get(3);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage(0,0,1);
        action.execute(controller);
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[1].getTopCard() == cardToBuy);
    }

    @Test
    @DisplayName("Test cards market renew")
    public void testCardsMaketRenew(){
        DevelopmentCard cardToBuy = game.getCardMarket().getDecks()[0][0].get(3);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        BuyDevelopmentCardMessage action = new BuyDevelopmentCardMessage(0,0,1);
        action.execute(controller);
        Assertions.assertEquals(game.getCardMarket().getDecks()[0][0].size(), 3);
        Assertions.assertTrue(game.getCardMarket().getDecks()[0][0].get(2) != cardToBuy);
    }
}
