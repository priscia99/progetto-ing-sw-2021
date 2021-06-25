package it.polimi.ingsw.model.player_board.faith_path;

import it.polimi.ingsw.server.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.server.model.player_board.faith_path.PopeCell;
import it.polimi.ingsw.server.model.player_board.faith_path.PopeFavor;
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
        Assertions.assertEquals(0, faithPath.getFaithPoints(), "Path position should be set at zero");
    }

    @Test
    @DisplayName("Test reaching a new position")
    public void testReachNewPosition(){
        int initPosition = faithPath.getFaithPoints();
        Assertions.assertEquals(0, faithPath.getFaithPoints(), "Position should be 0");
        faithPath.goToNextCell();
        Assertions.assertEquals(initPosition+1, faithPath.getFaithPoints(), "Path should be increase by 1");
        Assertions.assertEquals(1, faithPath.getFaithPoints(), "Position should be 1");
        //Assertions.assertEquals(1, faithPath.getFaithPoints(), "Position should be 1");
    }

    @Test
    @DisplayName("Test player received victory points from a PointsCell")
    public void testPointsFromPointsCell(){
        while(faithPath.getFaithPoints() != 3){
            faithPath.goToNextCell();
        }
        Assertions.assertEquals(3, faithPath.getFaithPoints(), "Position should be 3");
        Assertions.assertEquals(1, faithPath.getVictoryPoints(), "Player should have 1 victory point");

        while(faithPath.getFaithPoints() != 6){
            faithPath.goToNextCell();
        }
        Assertions.assertEquals(6, faithPath.getFaithPoints(), "Position should be 6");
        Assertions.assertEquals(3, faithPath.getVictoryPoints(), "Player should have 3 victory points");
    }

    @Test
    @DisplayName("Test player received victory points from a PopeFavor cell")
    public void testPointsFromPopeFavor(){
        while(faithPath.getFaithPoints() != 8){
            faithPath.goToNextCell();
        }
        Assertions.assertEquals(8, faithPath.getFaithPoints(), "Position should be 8");
        Assertions.assertTrue((faithPath.getCells()[8] instanceof PopeCell),"This Cell should be a PopeCell instance");
        PopeFavor favor = ((PopeCell) faithPath.getCells()[8]).getFavor();
        faithPath.checkPopeFavor(8);
        Assertions.assertEquals(5, faithPath.getVictoryPoints(), "Position should have 5 faith points(1+2+2PF)");
    }

    @Test
    @DisplayName("Test pope favor is set as used")
    public void testPopeFavorUsed(){
        while(faithPath.getFaithPoints() != 8){
            faithPath.goToNextCell();
        }
        faithPath.checkPopeFavor(8);
        Assertions.assertTrue((faithPath.getCells()[8] instanceof PopeCell),"This Cell should be a PopeCell instance");
        PopeFavor favor = ((PopeCell) faithPath.getCells()[8]).getFavor();
        Assertions.assertTrue(favor.isTriggered(), "Favor should be used");
    }

}
