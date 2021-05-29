package it.polimi.ingsw.model.game;

import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp(){
        game = new Game();
        game.setPlayers(MockProvider.getMockPlayers());
        game.setupVictoryObservations();
        game.setupLeaderCards();
        game.setupCardsMarket();
        game.setupMarbleMarket();
        game.giveLeaderCardsToPlayers();
        game.giveInitialResources();
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor(){
        Assertions.assertTrue(game!=null);
    }


    @Test
    @DisplayName("Test next turn switch current player correctly")
    public void testNextTurn(){
        game.setFirstPlayer();
        ArrayList<Player> players = game.getPlayers();
        Assertions.assertTrue(players.get(0).equals(game.getCurrentPlayer()));
        game.nextTurn();
        Assertions.assertTrue(players.get(1).equals(game.getCurrentPlayer()));
        game.nextTurn();
        Assertions.assertTrue(players.get(0).equals(game.getCurrentPlayer()));
    }

    @Test
    @DisplayName("Test game sets isFirst to first player")
    public void testSetFirstPlayer(){
        game.setFirstPlayer();
        Assertions.assertTrue(game.getPlayers().get(1).isFirst());
    }

    @Test
    @DisplayName("Test setup gives initial leader cards to player")
    public void testGiveLeaderCardsToPlayer(){
        Assertions.assertFalse(game.getPlayers().stream().map(player->player.getPlayerBoard()
                .getLeaderCardsDeck().getLeaderCards()
                .size()==4).collect(Collectors.toList()).contains(false));
    }

    @Test
    @DisplayName("Test at start, player have not starting leader cards ready")
    public void testAllPlayersHaveStartingLeaderCardsAtStart(){
        Assertions.assertFalse(game.allPlayersHaveStartingLeaderCards());
    }

    @Test
    @DisplayName("Test check players choose initial cards")
    public void testAllPlayersHaveStartingLeaderCard(){
        game.getPlayers().get(0).pickedLeaderCards(MockProvider.getArrayListLeaderCardsMock());
        game.getPlayers().get(1).pickedLeaderCards(MockProvider.getArrayListLeaderCardsMock());
        Assertions.assertTrue(game.allPlayersHaveStartingLeaderCards());
    }
}
