package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.service_message.GameBackupMessage;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.model.game.Game;

/**
 * Class used to manage backup of game
 */
public class BackupManager {
    private Game backup;
    private final Lobby lobby;

    public BackupManager(Lobby lobby){
        this.lobby = lobby;
    };

    /**
     * Save a new version of backup
     * @param backup
     */
    public void load(Game backup){
        this.backup = backup;
    }

    /**
     * Send backup to clients, and apply it to lobby
     * @throws Exception
     */
    public void applyBackup() throws Exception {
        lobby.setGame(backup);
        lobby.sendBroadcast(new GameBackupMessage(backup));
    }

    public Game getBackup(){
        return this.backup;
    }
}
