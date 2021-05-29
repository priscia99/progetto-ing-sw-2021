package it.polimi.ingsw.model.storage;

import it.polimi.ingsw.server.model.player_board.storage.Strongbox;
import it.polimi.ingsw.server.model.resource.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StrongboxTest {

    private Strongbox strongbox;

    @BeforeEach
    public void setup(){
        this.strongbox = new Strongbox();   // init a new strongbox
    }

    @Test
    @DisplayName("Test if strongbox is empty after init")
    public void testEmptyStrongbox(){
        // Check if the strongbox is empty
        Assertions.assertTrue(this.strongbox.isEmpty());
        // Check if none of resource types are present inside the strongbox
        Assertions.assertFalse(this.strongbox.contains(ResourceType.SERVANT));
        Assertions.assertFalse(this.strongbox.contains(ResourceType.COIN));
        Assertions.assertFalse(this.strongbox.contains(ResourceType.SHIELD));
        Assertions.assertFalse(this.strongbox.contains(ResourceType.STONE));
        // Check if count is equal to 0 for each resource type
        Assertions.assertEquals(0, this.strongbox.countByResourceType(ResourceType.SERVANT));
        Assertions.assertEquals(0, this.strongbox.countByResourceType(ResourceType.COIN));
        Assertions.assertEquals(0, this.strongbox.countByResourceType(ResourceType.SHIELD));
        Assertions.assertEquals(0, this.strongbox.countByResourceType(ResourceType.STONE));
    }

    @Test
    @DisplayName("Test resources insertion inside strongbox")
    public void testResourcesInsertion(){
        this.strongbox.addResource(ResourceType.STONE);
        this.strongbox.addResource(ResourceType.STONE);
        this.strongbox.addResource(ResourceType.STONE);
        this.strongbox.addResource(ResourceType.COIN);
        this.strongbox.addResource(ResourceType.SERVANT);
        this.strongbox.addResource(ResourceType.SERVANT);
        this.strongbox.addResource(ResourceType.SHIELD);
        Assertions.assertFalse(strongbox.isEmpty());
        Assertions.assertEquals(3, this.strongbox.countByResourceType(ResourceType.STONE));
        Assertions.assertEquals(1, this.strongbox.countByResourceType(ResourceType.COIN));
        Assertions.assertEquals(2, this.strongbox.countByResourceType(ResourceType.SERVANT));
        Assertions.assertEquals(1, this.strongbox.countByResourceType(ResourceType.SHIELD));
    }

    @Test
    @DisplayName("Test resources removal from strongbox")
    public void testResourcesRemoval(){
        // Insert some resources inside strongbox
        testResourcesInsertion();
        // Testing resources removal
        this.strongbox.removeResource(ResourceType.STONE);
        this.strongbox.removeResource(ResourceType.STONE);
        Assertions.assertEquals(1, this.strongbox.countByResourceType(ResourceType.STONE));
        this.strongbox.removeResource(ResourceType.COIN);
        Assertions.assertEquals(0, this.strongbox.countByResourceType(ResourceType.COIN));
    }
}
