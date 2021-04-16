package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.player_board.storage.Warehouse;

import java.util.ArrayList;
import java.util.Map;

public class StartProductionParams {
    final Warehouse warehouse;
    final Strongbox strongbox;
    final ArrayList<ProductionEffect> productionToActivate;

    public Warehouse getWarehouse() { return warehouse; }

    public Strongbox getStrongbox() { return strongbox; }

    public ArrayList<ProductionEffect> getProductionToActivate() {
        return productionToActivate;
    }

    private StartProductionParams(Warehouse warehouse, Strongbox strongbox, ArrayList<ProductionEffect> productions){
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.productionToActivate = productions;
    }

    static public StartProductionParams fromMap(Map<String, Object> map){
        try{
            Warehouse warehouse = (Warehouse) map.get("warehouse");
            Strongbox strongbox = (Strongbox) map.get("strongbox");
            ArrayList<ProductionEffect> productionSelected = (ArrayList<ProductionEffect>) map.get("productions");
            return new StartProductionParams(warehouse, strongbox, productionSelected);
        } catch (Exception e){
            throw new ParamsConvertionException("Error while converting play leader card params");
        }
    }
}
