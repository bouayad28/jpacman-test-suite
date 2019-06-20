package nl.tudelft.jpacman.integration.movement;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * System tests for basic game mechanics derived from user story 2.
 */
class PelletConsumeTest {
    
    private static final int PELLET_POINTS = 10;
    
    private Launcher launcher;
    
    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    void before() {
        launcher = new Launcher();
    }
    
    /**
     * Close the user interface.
     */
    @AfterEach
    void after() {
        launcher.dispose();
    }
    
    /**
     * Tests scenario S2.1.
     */
    @Test
    void eatPellet() {
        launcher.withMapFile("/player_2_pellets_map.txt");
        launcher.makeGame();
        launcher.launch();
        getGame().start();
    
        Player player = getGame().getPlayers().get(0);
        getGame().move(player, Direction.EAST);
        
        assertThat(getGame().getLevel().getBoard().squareAt(1, 0).getOccupants())
            .containsOnly(player);
        assertThat(getGame().getLevel().getBoard().squareAt(0, 0).getOccupants())
            .isEmpty();
        assertThat(player.getScore()).isEqualTo(PELLET_POINTS);
    }
    
    /**
     * Tests scenario S2.2.
     */
    @Test
    void moveToEmptySquare() {
        launcher.withMapFile("/player_empty_pellet_map.txt");
        launcher.makeGame();
        launcher.launch();
        getGame().start();
        
        Player player = getGame().getPlayers().get(0);
        getGame().move(player, Direction.EAST);
    
        assertThat(getGame().getLevel().getBoard().squareAt(1, 0).getOccupants())
            .containsOnly(player);
        assertThat(getGame().getLevel().getBoard().squareAt(0, 0).getOccupants())
            .isEmpty();
        assertThat(player.getScore()).isEqualTo(0);
    }
    
    /**
     * Tests scenario S2.3.
     */
    @Test
    void moveIntoWall() {
        launcher.withMapFile("/player_wall_map.txt");
        launcher.makeGame();
        launcher.launch();
        getGame().start();
        
        Player player = getGame().getPlayers().get(0);
        getGame().move(player, Direction.EAST);
        
        assertThat(getGame().getLevel().getBoard().squareAt(0, 0).getOccupants())
            .containsOnly(player);
        assertThat(getGame().getLevel().getBoard().squareAt(1, 0).getOccupants())
            .isEmpty();
    }
    
    /**
     * Tests scenario S2.4.
     */
    @Test
    void moveIntoGhost() {
        launcher.withMapFile("/player_ghost_map.txt");
        launcher.makeGame();
        launcher.launch();
        getGame().start();
        
        Player player = getGame().getPlayers().get(0);
        Ghost ghost = (Ghost) getGame().getLevel().getBoard().squareAt(1, 0).getOccupants().get(0);
        getGame().move(player, Direction.EAST);
        
        assertThat(getGame().getLevel().getBoard().squareAt(1, 0).getOccupants())
            .contains(ghost, player);
        assertThat(player.isAlive()).isFalse();
        assertThat(getGame().isInProgress()).isFalse();
    }
    
    /**
     * Tests scenario S2.5.
     */
    @Test
    void eatLastPellet() {
        launcher.withMapFile("/player_empty_pellet_map.txt");
        launcher.makeGame();
        launcher.launch();
        getGame().start();
        
        Player player = getGame().getPlayers().get(0);
        getGame().move(player, Direction.WEST);
    
        assertThat(player.isAlive()).isTrue();
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getGame().getLevel().remainingPellets()).isZero();
    }
    
    private Game getGame() {
        return launcher.getGame();
    }
}
