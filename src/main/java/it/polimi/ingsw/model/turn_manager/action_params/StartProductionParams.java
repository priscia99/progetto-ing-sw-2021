package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.player_board.storage.Storage;
import it.polimi.ingsw.model.player_board.storage.Warehouse;

import java.util.ArrayList;
import java.util.Map;

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
