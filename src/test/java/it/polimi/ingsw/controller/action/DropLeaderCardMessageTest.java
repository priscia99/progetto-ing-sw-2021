package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.network.message.from_client.DropLeaderCardMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DropLeaderCardMessageTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp(){
        game = MockProvider.getMockGame();
    }

    @Test
    @DisplayName("Test leader is removed")
    public void testLeaderIsRemoved(){
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        int deckSize = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size();
        LeaderCard cardToDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0);
        DropLeaderCardAction action = new DropLeaderCardAction(cardId);
        action.execute(game);
        int deckSizeAfterDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size();
        Assertions.assertEquals(deckSize -1, deckSizeAfterDrop );
        for(LeaderCard card : game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards()){
            Assertions.assertTrue(card != cardToDrop);
        }
    }

    @Test
    @DisplayName("Test player gain faith point")
    public void testPlayerGainFaithPoint(){
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        int faithPoints = game.getCurrentPlayer().getPlayerBoard().getFaithPath().getFaithPoints();
        DropLeaderCardAction action = new DropLeaderCardAction(cardId);
        action.execute(game);
        int faithPointsAfterDrop = game.getCurrentPlayer().getPlayerBoard().getFaithPath().getFaithPoints();
        Assertions.assertEquals(faithPointsAfterDrop, faithPoints +1);
    }
}
