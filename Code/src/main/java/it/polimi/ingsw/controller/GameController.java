package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;

public class GameController {
    private Game game;

    GameController(Game game){
        this.game = game;
    }

    public void setupGame(List<Player> players){
        game.setup(players);
    }

    public void playerHasChosenLeaders(Player player, List<LeaderCard> leaderCards){
        game.getPlayers().get(game.getPlayers().indexOf(player)).pickedLeaderCards(leaderCards);
        if(game.allPlayersHaveStartingLeaderCards()) game.giveInitialResources();
    }

    public void playerHasChosenInitialResource(Player player, List<ResourceType> resourcePiles, List<Integer> depots){
        for(int i = 0; i<resourcePiles.size(); i++){
            game.getPlayers().get(game.getPlayers().indexOf(player)).addResourceToDepot(resourcePiles.get(i), depots.get(i));
            game.getPlayers().get(game.getPlayers().indexOf(player)).hasChosenInitialResource();
        }
        if(game.allPlayersHasChosedInitialResources()) startGame();
    }

    private void startGame(){
        game.setFirstPlayer();
        game.start();

    }
}
