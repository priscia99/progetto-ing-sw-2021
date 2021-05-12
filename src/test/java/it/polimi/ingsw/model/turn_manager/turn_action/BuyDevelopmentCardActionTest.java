package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.turn_manager.action_params.BuyDevelopmentCardParams;
import it.polimi.ingsw.testUtils.MockProvider;
import it.polimi.ingsw.testUtils.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyDevelopmentCardActionTest {

    private BuyDevelopmentCardAction action;
    private Game game;

    @BeforeEach
    public void setUp(){
        action = new BuyDevelopmentCardAction();
        game = MockProvider.getMockGame();
    }

    @Test
    @DisplayName("Test player has new card")
    public void testPlayerHasNewCard(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("xPosition", 0);
        map.put("yPosition", 0);
        map.put("deckIndex", 1);
        BuyDevelopmentCardParams params = BuyDevelopmentCardParams.fromMap(map);
        Assertions.assertEquals(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 0);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        action.execute(game, params);
        Assertions.assertEquals(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsNumber(), 1);
    }

    @Test
    @DisplayName("Test player get bought card")
    public void testPlayerGetBoughtCard(){
        HashMap<String, Object> map = new HashMap<>();
        DevelopmentCard cardToBuy = game.getCardMarket().getDecks()[0][0].get(3);
        map.put("xPosition", 0);
        map.put("yPosition", 0);
        map.put("deckIndex", 1);
        BuyDevelopmentCardParams params = BuyDevelopmentCardParams.fromMap(map);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        action.execute(game, params);
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsDecks()[1].getTopCard() == cardToBuy);
    }

    @Test
    @DisplayName("Test cards market renew")
    public void testCardsMaketRenew(){
        HashMap<String, Object> map = new HashMap<>();
        DevelopmentCard cardToBuy = game.getCardMarket().getDecks()[0][0].get(3);
        map.put("xPosition", 0);
        map.put("yPosition", 0);
        map.put("deckIndex", 1);
        BuyDevelopmentCardParams params = BuyDevelopmentCardParams.fromMap(map);
        TestHelper.makeHimRich(game.getCurrentPlayer());
        action.execute(game, params);
        Assertions.assertEquals(game.getCardMarket().getDecks()[0][0].size(), 3);
        Assertions.assertTrue(game.getCardMarket().getDecks()[0][0].get(2) != cardToBuy);
    }
}
