package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.server.model.player_board.storage.Warehouse;
import it.polimi.ingsw.server.model.resource.ResourceType;
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
    public void testResourcesInsertion() throws GameException {
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
    public void testFullDepot() throws GameException {
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
    public void testRemovalFromSingleDepot() throws GameException {
        // Check if depot is originally empty
        Assertions.assertTrue(this.warehouse.isEmpty());
        // Check if depot capacity is correct
        Assertions.assertEquals(2, this.warehouse.getDepot(1).getCapacity());
        // Adding new resources until the depot is full
        this.warehouse.addToDepot(1, ResourceType.SERVANT);
        Assertions.assertFalse(this.warehouse.getDepot(1).isFull());
        Assertions.assertEquals(1, this.warehouse.getDepot(1).getQuantity());
        this.warehouse.addToDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(2, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertEquals(2, this.warehouse.countByResourceType(ResourceType.SERVANT));
        Assertions.assertTrue(this.warehouse.getDepot(1).isFull());
        Assertions.assertFalse(this.warehouse.getDepot(1).isEmpty());
        // Removing resources from depot until it is empty
        this.warehouse.removeFromDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(1, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertFalse(this.warehouse.getDepot(1).isEmpty());
        Assertions.assertFalse(this.warehouse.getDepot(1).isFull());
        this.warehouse.removeFromDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(0, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertTrue(this.warehouse.getDepot(1).isEmpty());
        Assertions.assertFalse(this.warehouse.getDepot(1).isFull());
    }

    @Test
    @DisplayName("Inserting resources until the entire warehouse is full")
    public void testInsertingUntilFullWarehouse() throws GameException {
        Assertions.assertTrue(warehouse.isEmpty());
        Assertions.assertFalse(warehouse.isFull());
        while(!warehouse.getDepot(0).isFull()){
            warehouse.addToDepot(0,ResourceType.SHIELD);
        }
        while(!warehouse.getDepot(1).isFull()){
            warehouse.addToDepot(1,ResourceType.COIN);
        }
        while(!warehouse.getDepot(2).isFull()){
            warehouse.addToDepot(2,ResourceType.STONE);
        }
        Assertions.assertFalse(warehouse.isEmpty());
        Assertions.assertTrue(warehouse.isFull());
    }

    @Test
    @DisplayName("Test swapping a depot")
    public void testDepotSwap() throws GameException {
        // Adding resources to depots
        this.warehouse.addToDepot(0, ResourceType.COIN);
        Assertions.assertEquals(ResourceType.COIN, this.warehouse.getDepot(0).getResourceType());
        Assertions.assertEquals(1, this.warehouse.getDepot(0).getQuantity());
        this.warehouse.addToDepot(1, ResourceType.SERVANT);
        Assertions.assertEquals(ResourceType.SERVANT, this.warehouse.getDepot(1).getResourceType());
        Assertions.assertEquals(1, this.warehouse.getDepot(1).getQuantity());
        // Checking depots capacity
        Assertions.assertEquals(1, this.warehouse.getDepot(0).getCapacity());
        Assertions.assertEquals(2, this.warehouse.getDepot(1).getCapacity());
        // Swapping depots
        this.warehouse.swap(0,1);
        Assertions.assertEquals(ResourceType.SERVANT, this.warehouse.getDepot(0).getResourceType());
        Assertions.assertEquals(ResourceType.COIN, this.warehouse.getDepot(1).getResourceType());
        Assertions.assertEquals(1, this.warehouse.getDepot(0).getQuantity());
        Assertions.assertEquals(1, this.warehouse.getDepot(1).getQuantity());
        Assertions.assertEquals(1, this.warehouse.getDepot(0).getCapacity());
        Assertions.assertEquals(2, this.warehouse.getDepot(1).getCapacity());
    }

    @Test
    @DisplayName("Test counting by resource type")
    public void testCountingByResourceType() throws GameException {
        // Adding several resources to depots in warehouse
        this.warehouse.addToDepot(0, ResourceType.SERVANT);
        this.warehouse.addToDepot(2, ResourceType.COIN);
        this.warehouse.addToDepot(1, ResourceType.STONE);
        this.warehouse.addToDepot(2, ResourceType.COIN);
        // Checking if counts by resource types are correct
        Assertions.assertEquals(1, this.warehouse.countByResourceType(ResourceType.SERVANT));
        Assertions.assertEquals(1, this.warehouse.countByResourceType(ResourceType.STONE));
        Assertions.assertEquals(2, this.warehouse.countByResourceType(ResourceType.COIN));
    }
}
