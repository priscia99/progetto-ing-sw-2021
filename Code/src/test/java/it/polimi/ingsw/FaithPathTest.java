package it.polimi.ingsw;

import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.model.player_board.faith_path.PopeCell;
import it.polimi.ingsw.model.player_board.faith_path.PopeFavor;
import org.junit.jupiter.api.*;

public class FaithPathTest
{
    public FaithPath faithPath;

    @BeforeEach
    public void setUp(){
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
        int initPosition = faithPath.getPosition();
        Assertions.assertEquals(0, faithPath.getFaithPoints(), "Position should be 0");
        faithPath.goToNextCell();
        Assertions.assertEquals(initPosition+1, faithPath.getPosition(), "Path should be increase by 1");
        Assertions.assertEquals(1, faithPath.getPosition(), "Position should be 1");
        //Assertions.assertEquals(1, faithPath.getFaithPoints(), "Position should be 1");
    }

    @Test
    @DisplayName("Test player received faith points from a PointsCell")
    public void testPointsFromPointsCell(){
        while(faithPath.getPosition() != 3){
            faithPath.goToNextCell();
        }
        Assertions.assertEquals(3, faithPath.getPosition(), "Position should be 3");
        Assertions.assertEquals(1, faithPath.getFaithPoints(), "Player should have 1 faith point");

        while(faithPath.getPosition() != 6){
            faithPath.goToNextCell();
        }
        Assertions.assertEquals(6, faithPath.getPosition(), "Position should be 6");
        Assertions.assertEquals(3, faithPath.getFaithPoints(), "Player should have 3 faith points");
    }

    @Test
    @DisplayName("Test player received faith points from a PopeFavor cell")
    public void testPointsFromPopeFavor(){
        while(faithPath.getPosition() != 8){
            faithPath.goToNextCell();
        }
        Assertions.assertEquals(8, faithPath.getPosition(), "Position should be 8");
        Assertions.assertTrue((faithPath.getCells()[7] instanceof PopeCell),"This Cell should be a PopeCell instance");
        PopeFavor favor = ((PopeCell) faithPath.getCells()[7]).getFavor();
        Assertions.assertFalse(favor.isUsed(), "Favor should NOT be used");
        faithPath.checkPopeFavor(8);
        Assertions.assertEquals(5, faithPath.getFaithPoints(), "Position should have 5 faith points(1+2+2PF)");
    }

    @Test
    @DisplayName("Test pope favor is set as used")
    public void testPopeFavorUsed(){
        while(faithPath.getPosition() != 8){
            faithPath.goToNextCell();
        }
        faithPath.checkPopeFavor(8);
        Assertions.assertTrue((faithPath.getCells()[7] instanceof PopeCell),"This Cell should be a PopeCell instance");
        PopeFavor favor = ((PopeCell) faithPath.getCells()[7]).getFavor();
        Assertions.assertTrue(favor.isUsed(), "Favor should be used");
    }

}
