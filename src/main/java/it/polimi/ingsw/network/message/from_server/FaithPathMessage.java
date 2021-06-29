package it.polimi.ingsw.network.message.from_server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.player_board.faith_path.FaithPath;

import java.io.Serializable;
import java.util.ArrayList;

public class FaithPathMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 30L;
    private final int faithPoints;
    private final ArrayList<Boolean> popeFavors;

    public FaithPathMessage(FaithPath faithPath, String username) {

        super.setPlayerUsername(username);
        this.faithPoints = faithPath.getFaithPoints();
        this.popeFavors = faithPath.getAcquiredPopeFavours();
    }

    public void execute(ClientController target) {
        target.refreshFaithPath(faithPoints, popeFavors, super.playerUsername);
    }
}
