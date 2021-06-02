package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.network.message.from_server.ChooseInitialLeadersMessage;
import it.polimi.ingsw.network.message.from_server.ChooseInitialResourcesMessage;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.server.model.player_board.PlayerBoard;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class Player2 {

    private final String id;
    private final String username;
    private boolean first = false;
    private final PlayerBoard playerBoard;

    public Player2(String id, String username) {
        this.id = id;
        this.username = username;
        this.playerBoard = new PlayerBoard();
    }

    public Player2(String id, String username, boolean first, PlayerBoard playerBoard) {
        this.id = id;
        this.username = username;
        this.first = first;
        this.playerBoard = playerBoard;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isFirst() {
        return first;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }
}
