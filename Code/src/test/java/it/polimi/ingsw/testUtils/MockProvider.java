package it.polimi.ingsw.testUtils;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.DepotEffect;
import it.polimi.ingsw.model.card.requirement.Requirement;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class MockProvider {
    static public ArrayList<Player> getMockPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Pippo"));
        players.add(new Player("Banano"));
        return players;
    }

    static public LeaderCard getLeaderCardMock(){
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

    static public ArrayList<LeaderCard> getArrayListLeaderCardsMock(){
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(getLeaderCardMock());
        leaderCards.add(getLeaderCardMock());
        return leaderCards;
    }

}