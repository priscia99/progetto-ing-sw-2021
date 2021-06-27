package it.polimi.ingsw.controller.action;


import it.polimi.ingsw.network.message.from_client.ChosenInitialLeadersMessage;
import it.polimi.ingsw.network.message.from_client.DropLeaderCardMessage;
import it.polimi.ingsw.network.message.from_client.PlayLeaderCardMessage;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.color.ColorPile;
import it.polimi.ingsw.server.model.card.requirement.ColorRequirement;
import it.polimi.ingsw.server.model.card.requirement.Requirement;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.testUtils.MockProvider;
import it.polimi.ingsw.testUtils.TestHelper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardsTest {
    private Game game;
    private ServerController controller;

    @BeforeEach
    public void setUp() throws Exception {
        game = MockProvider.getMockGame();
        controller = new ServerController(game);
    }

    @Test
    @DisplayName("Test player pick leader cards correctly")
    public void testPlayerPickLeaderCards(){
        // Check if player has 4 leader cards at the beginning
        assertEquals(4, game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size());

        // Player pick two leader cards from his deck correctly
        ArrayList<String> chosenCards = new ArrayList<>(
                game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards()
                .stream()
                .map(card -> card.getId())
                .collect(Collectors.toCollection(ArrayList::new)).subList(0,2));

        ChosenInitialLeadersMessage message = new ChosenInitialLeadersMessage(chosenCards, game.getCurrentPlayer().getNickname());
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });

        // Check if the player has now two leader cards instead of 4
        assertEquals(2, game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size());
        assertTrue(
            game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards()
                    .stream()
                    .map(card -> card.getId())
                    .collect(Collectors.toCollection(ArrayList::new)).equals(chosenCards)
        );
    }

    @Test
    @DisplayName("Test player drop leader card")
    public void testPlayerDropLeaderCards(){
        // Initialize game picking two leader cards
        testPlayerPickLeaderCards();
        // Getting player faith points
        int playerInitialFaithPoints = game.getCurrentPlayer().getPlayerBoard().getFaithPath().getFaithPoints();
        // Choose the leader card to drop from player deck
        String cardToDrop = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0).getId();
        // Check if card is present in client deck
        assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().anyMatch(
                card -> card.getId() == cardToDrop
        ));
        // Create the proper message and check and try to execute
        DropLeaderCardMessage message = new DropLeaderCardMessage(cardToDrop);
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });
        // Check if player doesn't have the dropped card in its deck anymore
        assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().noneMatch(
                card -> card.getId() == cardToDrop
        ));
        // Check if player faith points have increased by one
        assertEquals(game.getCurrentPlayer().getPlayerBoard().getFaithPath().getFaithPoints(), playerInitialFaithPoints+1);
    }

    @Test
    @DisplayName("Test player try to play a leader card successfully")
    public void testPlayerPlayLeaderCardSuccessfully(){
        LeaderCard card = MockProvider.getLeaderCardMock();
        game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().addLeader(card);
        String cardId = card.getId();
        int index = (int)(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().stream().count()) -1;
        Assertions.assertFalse(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(index).isActive());
        PlayLeaderCardMessage message = new PlayLeaderCardMessage(cardId);
        assertDoesNotThrow(() -> {
            message.execute(controller);
        });
        Assertions.assertTrue(game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(index).isActive());
    }

    @Test
    @DisplayName("Test player try to play leader card without fulfilling requirements")
    public void testPlayerPlayLeaderCardNoFulfilling(){
        // Initialize game picking two leader cards
        testPlayerPickLeaderCards();
        LeaderCard cardToActivate = game.getCurrentPlayer().getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0);
        // Check card is not active yet
        assertFalse(cardToActivate.isActive());
        Requirement cardRequirement = cardToActivate.getRequirement();  // retrieve requirements from leader card
        PlayLeaderCardMessage message = new PlayLeaderCardMessage(cardToActivate.getId());
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "Leader requirements are not fulfilled!";
        assertTrue(exception.getMessage().contains(expectedMessage));
        // Check if card is still not active
        assertFalse(cardToActivate.isActive());
    }

    @Test
    @DisplayName("Test player try to play an invalid leader card")
    public void testPlayerPlayInvalidLeaderCard(){
        PlayLeaderCardMessage message = new PlayLeaderCardMessage("L90");
        Exception exception = assertThrows(Exception.class, () -> {
            message.execute(controller);
        });
        String expectedMessage = "Leader not found!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
