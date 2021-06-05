package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;

public class GameView extends View implements Observer<Pair<String, Boolean>> {

    public GameView(UI userInterface) {
        super(userInterface);
    }

    @Override
    public void update(Pair<String, Boolean> object) {
        userInterface.displayNewTurn(object.getFirst(), object.getSecond());
    }
}
