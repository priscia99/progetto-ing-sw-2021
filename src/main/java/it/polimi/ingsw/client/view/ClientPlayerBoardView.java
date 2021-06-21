package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.ClientPlayerBoard;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class ClientPlayerBoardView extends View implements Observer<ClientPlayerBoard> {
    public ClientPlayerBoardView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(ClientPlayerBoard playerBoard) {
        userInterface.displayUserStats(playerBoard);
    }
}
