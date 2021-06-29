package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientPlayerBoard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.game.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class PlayersOrderMessage extends Message<ClientController> implements Serializable{

    private static final long serialVersionUID = 16L;
    private final ArrayList<String> playersOrder;

    public PlayersOrderMessage(ArrayList<Player> players) {
        this.playersOrder = new ArrayList<>();
        for(int i = 0; i<players.size(); i++){
            playersOrder.add(players.get(i).getNickname());
        }
    }

    public void execute(ClientController target) {
        LinkedHashMap<String, ClientPlayerBoard> ordered = new LinkedHashMap();
        int playerOrder = 1;
        for(String username : playersOrder){
            ClientPlayerBoard tempBoard = target.getGame().getPlayerBoardMap().get(username);
            if(tempBoard.isMine()){
                tempBoard.setOrder(0);
            }
            else {
                tempBoard.setOrder(playerOrder++);
            }
            ordered.put(username, tempBoard);
        }
        target.startUI();
        target.getGame().setPlayerBoardMap(ordered);
    }

}