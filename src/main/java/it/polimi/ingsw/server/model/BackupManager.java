package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.service_message.GameBackupMessage;
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

    public void applyBackup() throws Exception {
        lobby.setGame(backup);
        lobby.sendBroadcast(new GameBackupMessage(backup));
    }

    public Game getBackup(){
        return this.backup;
    }
}
