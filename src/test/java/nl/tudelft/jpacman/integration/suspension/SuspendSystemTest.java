package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * System tests for basic game mechanics derived from user story 4.
 */
class SuspendSystemTest {
    
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
     * Tests scenario S4.1.
     */
    @Test
    void gameIsPaused() {
        launcher.launch();
        
        getGame().start();
        getGame().stop();
        
        assertThat(getGame().isInProgress()).isFalse();
    }
    
    /**
     * Tests scenario S4.2.
     */
    @Test
    void gameIsUnpaused() {
        launcher.launch();
        
        getGame().start();
        getGame().stop();
        getGame().start();
        
        assertThat(getGame().isInProgress()).isTrue();
    }
    
    private Game getGame() {
        return launcher.getGame();
    }
}
