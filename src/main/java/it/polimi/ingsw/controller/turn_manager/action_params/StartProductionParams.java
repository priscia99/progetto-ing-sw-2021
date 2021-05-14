package it.polimi.ingsw.controller.turn_manager.action_params;

import it.polimi.ingsw.server_model.card.effect.ProductionEffect;
import it.polimi.ingsw.server_model.player_board.storage.Storage;
import it.polimi.ingsw.server_model.player_board.storage.Warehouse;

import java.util.ArrayList;

public class StartProductionParams extends ActionParams {
    final Warehouse warehouse;
    final Storage strongbox;
    final ArrayList<ProductionEffect> productionToActivate;

    public Warehouse getWarehouse() { return warehouse; }

    public Storage getStrongbox() { return strongbox; }

    public ArrayList<ProductionEffect> getProductionToActivate() {
        return productionToActivate;
    }

    public StartProductionParams(Warehouse warehouse, Storage strongbox, ArrayList<ProductionEffect> productions){
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.productionToActivate = productions;
    }

}
