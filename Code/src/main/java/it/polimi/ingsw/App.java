package it.polimi.ingsw;

import it.polimi.ingsw.data.LeaderCardsBuilder;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class App
{
    public static void main( String[] args )
    {
        final String gameId = "1";
        final List<Player> players = new ArrayList<>();
        players.add(new Player("Pippo"));
        players.add(new Player("Banano"));
        Game game = new Game(gameId, players);
    }
}
