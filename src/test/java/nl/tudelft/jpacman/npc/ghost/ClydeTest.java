package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests the AI movement behaviour of Clyde.
 */
public class ClydeTest {

    private GhostMapParser mapParser;
    private Player player;
    
    /**
     * Sets up the map parser and player for use in the tests.
     */
    @BeforeEach
    void setup() {
    
        PacManSprites sprites = new PacManSprites();
        
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        player = playerFactory.createPacMan();
        
        GhostFactory ghostFactory = new GhostFactory(sprites);
        PointCalculator pointCalculator = new DefaultPointCalculator();
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        BoardFactory boardFactory = new BoardFactory(sprites);
        
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }
    
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
        
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
        
        assertThat(clyde.nextAiMove()).isEmpty();
    }
    
    /**
     * Checks that the next AI move is to the east when the player is on the square directly to the
     * west of Clyde.
     */
    @Test
    void nextToPlayerTest() {
        
        Level level = getLevelFromMapResource("/clyde_pacman_dist1_map.txt");
    
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
    
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }
    
    /**
     * Checks that the next AI move is to the east when the player is eight squares to the west of
     * Clyde.
     */
    @Test
    void eightFromPlayerTest() {
    
        Level level = getLevelFromMapResource("/clyde_pacman_dist8_map.txt");
    
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
    
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }
    
    /**
     * Checks that the next AI move is to the west when the player is nine squares to the west of
     * Clyde.
     */
    @Test
    void nineFromPlayerTest() {
        
        Level level = getLevelFromMapResource("/clyde_pacman_dist9_map.txt");
        
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
        
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }
    
    /**
     * Loads a level given a test map resource name.
     * @param mapName The name of the map test resource.
     * @return The level constructed from the map.
     */
    private Level getLevelFromMapResource(String mapName) {
    
        try {
            return mapParser.parseMap(mapName);
        } catch (IOException ioe) {
            fail("Could not load test map resource", ioe);
            // Never reached, just to make the compiler happy
            return null;
        }
    }
}
