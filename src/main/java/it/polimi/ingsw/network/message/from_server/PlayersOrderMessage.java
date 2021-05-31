package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.game.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlayersOrderMessage implements Message<ClientController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<String> playersOrder;

    public PlayersOrderMessage(ArrayList<Player> players) {
        this.playersOrder = new ArrayList<>();
        //First player is in position 1 of array
        for(int i = 1; i<players.size(); i++){
            playersOrder.add(players.get(i).getNickname());
        }
        playersOrder.add(players.get(0).getNickname());
    }

    public void execute(ClientController target) {
    }

}