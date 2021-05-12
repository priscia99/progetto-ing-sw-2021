package it.polimi.ingsw.model.turn_manager.turn_action;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.turn_manager.action_params.DropLeaderCardParams;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DropLeaderCardActionTest {
    private DropLeaderCardAction action;
    private Game game;

    @BeforeEach
    public void setUp(){
        action = new DropLeaderCardAction();
        game = MockProvider.getMockGame();
    }

    @Test
    @DisplayName("Test leader is removed")
    public void testLeaderIsRemoved(){
        HashMap<String, Object> map = new HashMap<>();
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        map.put("cardId", cardId);
        int deckSize = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size();
        LeaderCard cardToDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0);
        DropLeaderCardParams params = DropLeaderCardParams.fromMap(map);
        action.execute(game, params);
        int deckSizeAfterDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size();
        Assertions.assertEquals(deckSize -1, deckSizeAfterDrop );
        for(LeaderCard card : game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards()){
            Assertions.assertTrue(card != cardToDrop);
        }
    }

    @Test
    @DisplayName("Test player gain faith point")
    public void testPlayerGainFaithPoint(){
        HashMap<String, Object> map = new HashMap<>();
        String cardId = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        map.put("cardId", cardId);
        int faithPoints = game.getCurrentPlayer().getPlayerBoard().getFaithPath().getFaithPoints();
        DropLeaderCardParams params = DropLeaderCardParams.fromMap(map);
        action.execute(game, params);
        int faithPointsAfterDrop = game.getCurrentPlayer().getPlayerBoard().getFaithPath().getFaithPoints();
        Assertions.assertEquals(faithPointsAfterDrop, faithPoints +1);
    }
}
