package it.polimi.ingsw.client.view.ui.gui.controllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.server.model.marble.Marble;

public abstract class GenericGUIController {
    private ClientController clientController;

    public GenericGUIController(ClientController clientController) {
        this.clientController = clientController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    /**
     * Sets the client controller
     * @param clientController the client controller
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

}
