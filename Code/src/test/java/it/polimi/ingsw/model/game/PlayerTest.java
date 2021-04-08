package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.DepotEffect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    private Player player;

    private LeaderCard getLeaderCardMock(){
        return new LeaderCard(
                false,
                5,
                new DepotEffect(ResourceType.COIN, new Depot(2)),
                new Requirement() {
                    @Override
                    public boolean isFulfilled(Player player) {
                        return false;
                    }
                }
        );
    }

    @BeforeEach
    public void setUp(){
        player = new Player("Tested");
    }

    @Test
    @DisplayName("Test picked leader cards")
    public void testPickedLeaderCards(){
        List<LeaderCard> pickedCards = new ArrayList<>();
        LeaderCard cardToCheck = getLeaderCardMock();
        pickedCards.add(cardToCheck);
        player.pickedLeaderCards(pickedCards);
        Assertions.assertEquals(1, player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards().size());
        Assertions.assertEquals(cardToCheck, player.getPlayerBoard().getLeaderCardsDeck().getLeaderCards().get(0));
    }

    @Test
    @DisplayName("Test player has LeaderCards")
    public void testPlayerHasLeaderCards(){
        Assertions.assertFalse(player.hasLeaderCards());
        LeaderCard cardToAdd = getLeaderCardMock();
        player.getPlayerBoard().getLeaderCardsDeck().addLeader(cardToAdd);
        Assertions.assertTrue(player.hasLeaderCards());
    }
}
