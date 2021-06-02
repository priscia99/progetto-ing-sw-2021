package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.SetupMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.market.CardsMarket;
import it.polimi.ingsw.server.model.market.MarbleMarket;
import it.polimi.ingsw.server.model.player_board.LeaderCardsDeck;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game2 extends Observable<Message<ClientController>> implements Observer<Message<ClientController>>, Cloneable {

    private ArrayList<Player> players;
    private int currentPlayerIndex;

    private LeaderCardsDeck leaderCardsDeck;
    private CardsMarket cardsMarket;
    private MarbleMarket marbleMarket;

    private boolean setupPhase = false;
    private boolean lastRound = false;

    public Game2(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.lastRound = false;
        this.setupPhase = false;
        this.leaderCardsDeck = LeaderCardsDeck.getStartingDeck();
        this.cardsMarket = CardsMarket.getStartingMarket();
        this.marbleMarket = MarbleMarket.getStartingMarket();
    }

    public void setup() {
        this.setupPhase = true;
        this.lastRound = false;
        // give initial leader cards
        players.forEach(player -> {
            ArrayList<LeaderCard> initialCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                initialCards.add(leaderCardsDeck.pop());
            }
            player.getPlayerBoard().getLeaderCardsDeck().addLeaders(initialCards);
        });
        // calculate initial resources and assing faith points
        ArrayList<Integer> resourcesToChoose = this.calculateInitialResources();
        // notify setup
        // TODO consider implementing this logic only client
        // TODO in response there will be a function in controller that is triggered by a counter
        notify(new SetupMessage(
                players.stream().map(Player::getNickname).collect(Collectors.toCollection(ArrayList::new)),
                resourcesToChoose
                )
        );
    }

    private ArrayList<Integer> calculateInitialResources() {
        ArrayList<Integer> resources = new ArrayList<>();
        if(players.size()>1){
            resources.add(1);
            if(players.size()>2){
                resources.add(1);
                players.get(2).addFaithPoints(1);
                if(players.size()>3){
                    resources.add(2);
                    players.get(3).addFaithPoints(1);
                }
            }
        }
        return resources;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public LeaderCardsDeck getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    public CardsMarket getCardsMarket() {
        return cardsMarket;
    }

    public MarbleMarket getMarbleMarket() {
        return marbleMarket;
    }

    public boolean isSetupPhase() {
        return setupPhase;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    @Override
    public void update(Message<ClientController> object) {

    }
}
