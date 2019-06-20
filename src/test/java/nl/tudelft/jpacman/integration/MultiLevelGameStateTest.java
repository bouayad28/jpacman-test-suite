package nl.tudelft.jpacman.integration;

import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.MultiLevelGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * System tests based on game state transitions specific to multi level games.
 */
class MultiLevelGameStateTest extends GameStateTest {
    
    private static final int FINAL_LEVEL_NUMBER = 3;
    
    /**
     * Sets the multi level launcher as the game launcher for the tests.
     */
    @BeforeEach
    @Override
    void setupLauncher() {
        setLauncher(new MultiLevelLauncher());
    }
    
    /**
     * Tests that we can go from the won state to the started state by pressing the start button.
     * @param levelNumber The index of the level.
     */
    @ParameterizedTest
    @CsvSource({"0", "1", "2"})
    void wonThenStarted(int levelNumber) {
        setupWon(levelNumber);
        
        getGame().start();
        
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(getPlayer().isAlive()).isTrue();
        assertThat(getGame().getLevelNumber()).isEqualTo(levelNumber + 1);
        verify(getLevelObserver()).levelWon();
        verify(getLevelObserver(), times(0)).levelLost();
    }
    
    /**
     * Tests that for the final level we stay in the won state when the start button is pressed.
     */
    @Test
    void wonFinalLevelThenStarted() {
        setupWon(FINAL_LEVEL_NUMBER);
        
        getGame().start();
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        assertThat(getGame().getLevelNumber()).isEqualTo(FINAL_LEVEL_NUMBER);
        verify(getLevelObserver()).levelWon();
        verify(getLevelObserver(), times(0)).levelLost();
    }
    
    /**
     * Sets up a won game on the given level number.
     * @param levelNumber The last level that was won.
     */
    private void setupWon(int levelNumber) {
        setupWon();
        
        for (int i = 0; i < levelNumber; i++) {
            getGame().start();
            getGame().move(getPlayer(), Direction.WEST);
        }
    
        assert !getGame().isInProgress();
        assert getPlayer().isAlive();
        assert getGame().getLevelNumber() == levelNumber;
    }
    
    /**
     * Gets the multi level game from the launcher. Just a casting version of
     * {@link GameStateTest#getGame()} for convenience.
     * @return The multi level game instance.
     */
    @Override
    protected MultiLevelGame getGame() {
        return (MultiLevelGame) getLauncher().getGame();
    }
}
