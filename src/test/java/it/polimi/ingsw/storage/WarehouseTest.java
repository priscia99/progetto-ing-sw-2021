package it.polimi.ingsw.storage;

import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WarehouseTest {
    private Warehouse warehouse = null;

    @BeforeEach
    public void setup(){
        this.warehouse = new Warehouse();
    }

    @Test
    @DisplayName("Test insertion of a single resource")
    public void testResourcesInsertion(){
        // Check if warehouse is empty at the beginning
        Assertions.assertTrue(this.warehouse.isEmpty());
        // Add a new resourse into the warehouse
        this.warehouse.addToDepot(0, ResourceType.COIN);
        Assertions.assertEquals(this.warehouse.getDepot(0).getResourceType(), ResourceType.COIN);
        // Check if now the depot is not empty
        Assertions.assertEquals(1, this.warehouse.countByResourceType(ResourceType.COIN));
        Assertions.assertFalse(this.warehouse.isEmpty());
    }

    @Test
    @DisplayName("Test insertion of multiple resources until a single depot is full")
    public void testFullDepot(){
        this.warehouse.addToDepot(2,ResourceType.STONE);
        this.warehouse.addToDepot(2,ResourceType.STONE);
        this.warehouse.addToDepot(2,ResourceType.STONE);
        Assertions.assertEquals(ResourceType.STONE, this.warehouse.getDepot(2).getResourceType());
        Assertions.assertEquals(3, this.warehouse.countByResourceType(ResourceType.STONE));
        Assertions.assertFalse(this.warehouse.getDepot(2).isEmpty());
        Assertions.assertTrue(this.warehouse.getDepot(2).isFull());
    }

    @Test
    @DisplayName("Test removal of a single resource from depot")
    public void test(){
        Assertions.assertTrue(this.warehouse.isEmpty());
        Assertions.assertEquals(2, this.warehouse.getDepot(1).getCapacity());
        this.warehouse.addToDepot(1, ResourceType.SERVANT);
        Assertions.assertFalse(this.warehouse.getDepot(1).isFull());
        Assertions.assertEquals(1, this.warehouse.getDepot(1).getQuantity());
        this.warehouse.addToDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(2, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertEquals(2, this.warehouse.countByResourceType(ResourceType.SERVANT));
        Assertions.assertTrue(this.warehouse.getDepot(1).isFull());
        Assertions.assertFalse(this.warehouse.getDepot(1).isEmpty());
        this.warehouse.removeFromDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(1, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertFalse(this.warehouse.getDepot(1).isEmpty());
        Assertions.assertFalse(this.warehouse.getDepot(1).isFull());
        this.warehouse.removeFromDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(0, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertTrue(this.warehouse.getDepot(1).isEmpty());
        Assertions.assertFalse(this.warehouse.getDepot(1).isFull());

    }
}
