package nl.tudelft.jpacman.integration;

import nl.tudelft.jpacman.Launcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * System tests based on game state transitions specific to single level games.
 */
class SingleLevelGameStateTest extends GameStateTest {
    
    /**
     * Sets the single level launcher as the game launcher for the tests.
     */
    @BeforeEach
    @Override
    void setupLauncher() {
        setLauncher(new Launcher());
    }
    
    /**
     * Tests that we stay in the won state when the start button is pressed.
     */
    @Test
    void wonThenStarted() {
        setupWon();
        
        getGame().start();
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verify(getLevelObserver()).levelWon();
        verify(getLevelObserver(), times(0)).levelLost();
    }
}
