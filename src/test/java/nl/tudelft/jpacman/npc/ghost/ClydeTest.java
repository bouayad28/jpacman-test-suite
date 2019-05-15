package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the AI movement behaviour of Clyde.
 */
public class ClydeTest extends GhostTest {
    
    /**
     * Checks that the next AI move is empty when no player is present.
     */
    @Test
    void noPlayerTest() {
    
        Level level = getLevelFromMapResource("/only_clyde_map.txt");
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        
        assertThat(clyde.nextAiMove()).isEmpty();
    }
    
    /**
     * Checks that the next AI move is empty when the player is unreachable from the current
     * position.
     */
    @Test
    void unreachablePlayerTest() {
    
        Level level = getLevelFromMapResource("/unreachable_player_map.txt");
        level.registerPlayer(getPlayer());
    
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        
        assertThat(clyde.nextAiMove()).isEmpty();
    }
    
    /**
     * Checks that the next AI move is to the east when the player is on the square directly to the
     * west of Clyde.
     */
    @Test
    void nextToPlayerTest() {
        
        Level level = getLevelFromMapResource("/clyde_pacman_dist1_map.txt");
        level.registerPlayer(getPlayer());
    
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
    
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }
    
    /**
     * Checks that the next AI move is to the east when the player is eight squares to the west of
     * Clyde.
     */
    @Test
    void eightFromPlayerTest() {
    
        Level level = getLevelFromMapResource("/clyde_pacman_dist8_map.txt");
        level.registerPlayer(getPlayer());
    
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
    
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }
    
    /**
     * Checks that the next AI move is to the west when the player is nine squares to the west of
     * Clyde.
     */
    @Test
    void nineFromPlayerTest() {
        
        Level level = getLevelFromMapResource("/clyde_pacman_dist9_map.txt");
        level.registerPlayer(getPlayer());
    
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }
}
