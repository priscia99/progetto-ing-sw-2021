package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.ClientGame;

public class ClientController {

    private final ClientGame game;

    public ClientController(ClientGame game) {
        this.game = game;
    }

    public void setCurrentPlayer(String player){
        game.setCurrentPlayer(player);
    }

    public ClientGame getGame() {
        return game;
    }
}
