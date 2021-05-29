package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.ui.UI;

public abstract class View{

    protected UI userInterface;

    protected View(UI userInterface){
        this.userInterface = userInterface;
    }
}
