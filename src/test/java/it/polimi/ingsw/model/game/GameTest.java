package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.testUtils.MockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() throws GameException {
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
        try{
            game.setFirstPlayer();
            ArrayList<Player> players = game.getPlayers();
            game.nextTurn();
            Assertions.assertEquals(game.getCurrentPlayer(), players.get(0));
            game.nextTurn();
            Assertions.assertEquals(game.getCurrentPlayer(), players.get(1));
            game.nextTurn();
            Assertions.assertEquals(game.getCurrentPlayer(), players.get(0));
        } catch (GameException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("Test game sets isFirst to first player")
    public void testSetFirstPlayer(){
        game.setFirstPlayer();
        Assertions.assertTrue(game.getPlayers().get(0).isFirst());
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
    public void testAllPlayersHaveStartingLeaderCard() throws GameException {
        game.getPlayers().get(0).pickedLeaderCards(new ArrayList<>(MockProvider.getArrayListLeaderCardsMock().stream().map(LeaderCard::getId).collect(Collectors.toList())));
        game.getPlayers().get(1).pickedLeaderCards(new ArrayList<>(MockProvider.getArrayListLeaderCardsMock().stream().map(LeaderCard::getId).collect(Collectors.toList())));
        Assertions.assertTrue(game.allPlayersHaveStartingLeaderCards());
    }
}
