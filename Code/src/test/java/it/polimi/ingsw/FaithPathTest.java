package it.polimi.ingsw;

import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import org.junit.jupiter.api.*;

public class FaithPathTest
{
    public static FaithPath faithPath;

    @BeforeAll
    public static void setUp(){
        faithPath = FaithPath.getStandardFaithPath();
    }

    @Test
    @DisplayName("Test initial position")
    public void testInitialPosition(){
        Assertions.assertEquals(0, faithPath.getPosition(), "Path position should be set at zero");
    }

    @Test
    @DisplayName("Test reaching a new position")
    public void testReachNewPosition(){

    }
}
