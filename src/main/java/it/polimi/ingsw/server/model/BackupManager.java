package it.polimi.ingsw.server.model;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.from_server.GameBackupMessage;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.model.game.Game;

public class BackupManager {
    private Game backup;
    private final Lobby lobby;

    public BackupManager(Lobby lobby){
        this.lobby = lobby;
    };

    public void load(Game backup){
        this.backup = backup;
    }

    public void applyBackup() throws GameException {
        lobby.setGame(backup);
        lobby.sendBroadcast(new GameBackupMessage(backup));
    }
}
