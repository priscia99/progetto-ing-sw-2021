package it.polimi.ingsw.testUtils;

import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.card.requirement.Requirement;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.resource.ResourceType;

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
                5,
                new DepotEffect(ResourceType.COIN),
                new Requirement() {
                    @Override
                    public boolean isFulfilled(Player player) {
                        return false;
                    }
                }
        );
    }

    static public Game getMockGame(){
        Game game = new Game();
        ArrayList<Player> players = getMockPlayers();
        game.setup(players);
        game.nextTurn();
        return game;
    }

    static public ArrayList<LeaderCard> getArrayListLeaderCardsMock(){
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(getLeaderCardMock());
        leaderCards.add(getLeaderCardMock());
        return leaderCards;
    }

    static public ArrayList<DevelopmentCard> getArrayDevelopmentCardsMock(){
        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
        developmentCards.add(new DevelopmentCard(1, 1, null, Color.BLUE, null));
        developmentCards.add(new DevelopmentCard(1, 1, null, Color.BLUE, null));
        developmentCards.add(new DevelopmentCard(1, 1, null, Color.BLUE, null));
        developmentCards.add(new DevelopmentCard(1, 2, null, Color.BLUE, null));
        developmentCards.add(new DevelopmentCard(1, 2, null, Color.BLUE, null));
        developmentCards.add(new DevelopmentCard(1, 2, null, Color.BLUE, null));
        developmentCards.add(new DevelopmentCard(1, 3, null, Color.BLUE, null));
        return developmentCards;
    }

}
