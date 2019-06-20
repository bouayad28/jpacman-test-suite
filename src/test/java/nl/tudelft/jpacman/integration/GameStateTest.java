package nl.tudelft.jpacman.integration;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Generic system tests based on game state transitions.
 */
abstract class GameStateTest {
    
    private static final int MOVES_PLAYER_TO_GHOST = 5;
    private static final int MOVES_PLAYER_TO_PELLET = 6;
    private static final int MOVES_PLAYER_TO_WALL = 8;
    
    private Launcher launcher;
    private Level.LevelObserver levelObserver;
    
    /**
     * Mock a level observer instance which can be used in the tests.
     */
    @BeforeEach
    void before() {
        levelObserver = mock(Level.LevelObserver.class);
    }
    
    /**
     * Set the launcher instance to the desired (sub)class. Use {@link #setLauncher(Launcher)}.
     */
    @BeforeEach
    abstract void setupLauncher();
    
    /**
     * Close the user interface.
     */
    @AfterEach
    void after() {
        launcher.dispose();
    }
    
    /**
     * Tests that we can go from the running state to the started state.
     */
    @Test
    void runningThenStarted() {
        setupRunning(null);
        
        getGame().start();
    
        assertThat(getGame().isInProgress()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the running state to the paused state.
     */
    @Test
    void runningThenStopped() {
        setupRunning(null);
        
        getGame().stop();
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the running state to the won state.
     */
    @Test
    void runningThenEatLastPellet() {
        setupRunning("/player_empty_pellet_map.txt");
        
        getGame().move(getPlayer(), Direction.WEST);
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the running state to the started state by eating a pellet.
     */
    @Test
    void runningThenEatPellet() {
        setupRunning("/player_2_pellets_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the running state to the lost state.
     */
    @Test
    void runningThenMoveIntoGhost() {
        setupRunning("/player_ghost_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the running state to the started state by trying to move into a
     * wall.
     */
    @Test
    void runningThenMoveIntoWall() {
        setupRunning("/player_wall_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the running state to the started state by moving to an empty
     * space.
     */
    @Test
    void runningThenMoveIntoEmptySquare() {
        setupRunning("/player_empty_pellet_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Sets up a running game with a given map.
     * @param mapName The resource filename of the map to be used.
     */
    protected void setupRunning(String mapName) {
        if (mapName != null) {
            launcher.withMapFile(mapName);
        }
        launcher.makeGame();
        launcher.launch();
        getGame().getLevel().addObserver(levelObserver);
    }
    
    /**
     * Tests that we stay in the started state by pressing the start button.
     */
    @Test
    void startedThenStarted() {
        setupStarted(null);
        
        getGame().start();
        
        assertThat(getGame().isInProgress()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we can go from the started state to the paused state by pressing the start
     * button.
     */
    @Test
    void startedThenStopped() {
        setupStarted(null);
        
        getGame().stop();
        
        assertThat(getGame().isInProgress()).isFalse();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we can go from the started state to the won state by eating the last pellet.
     */
    @Test
    void startedThenEatLastPellet() {
        setupStarted("/player_empty_pellet_map.txt");
        
        getGame().move(getPlayer(), Direction.WEST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isZero();
        verify(levelObserver).levelWon();
        verify(levelObserver, times(0)).levelLost();
    }
    
    /**
     * Tests that we stay in the started state when we eat a pellet.
     */
    @Test
    void startedThenEatPellet() {
        setupStarted("/player_2_pellets_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(getPlayer().isAlive()).isTrue();
        assertThat(getGame().getLevel().remainingPellets()).isOne();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we can go from the started state to the lost state.
     */
    @Test
    void startedThenMoveIntoGhost() {
        setupStarted("/player_ghost_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Tests that we stay in the started state when we try to move into a wall.
     */
    @Test
    void startedThenMoveIntoWall() {
        setupStarted("/player_wall_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we stay in the started state when we move to an empty square.
     */
    @Test
    void startedThenMoveIntoEmptySquare() {
        setupStarted("/player_empty_pellet_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Sets up a started game with a given map.
     * @param mapName The resource filename of the map to be used.
     */
    protected void setupStarted(String mapName) {
        setupRunning(mapName);
        getGame().start();
        
        assert getGame().isInProgress();
    }
    
    /**
     * Tests that we stay in the won state when the stop button is pressed.
     */
    @Test
    void wonThenStopped() {
        setupWon();
        
        getGame().stop();
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verify(levelObserver).levelWon();
        verify(levelObserver, times(0)).levelLost();
    }
    
    /**
     * Tests that we cannot go from the won state to the lost state.
     */
    @Test
    void wonThenMoveIntoGhost() {
        setupWonWithGhost();
        
        for (int i = 0; i < MOVES_PLAYER_TO_GHOST + 2; i++) {
            getGame().move(getPlayer(), Direction.WEST);
        }
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verify(levelObserver).levelWon();
        verify(levelObserver, times(0)).levelLost();
    }
    
    /**
     * Tests that we cannot go from the won state to the started state by trying to move into a
     * wall.
     */
    @Test
    void wonThenMoveIntoWall() {
        setupWonWithGhost();
    
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verify(levelObserver).levelWon();
        verify(levelObserver, times(0)).levelLost();
    }
    
    /**
     * Tests that we cannot go from the won state to the started state by trying to move to an
     * empty space.
     */
    @Test
    void wonThenMoveIntoEmpty() {
        setupWonWithGhost();
        
        getGame().move(getPlayer(), Direction.WEST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verify(levelObserver).levelWon();
        verify(levelObserver, times(0)).levelLost();
    }
    
    /**
     * Sets up a won game in a simple map without a ghost.
     */
    protected void setupWon() {
        setupStarted("/player_empty_pellet_map.txt");
        getGame().move(getPlayer(), Direction.WEST);
    
        assert !getGame().isInProgress();
        assert getPlayer().isAlive();
    }
    
    /**
     * Sets up a won game in a simple map with a ghost, which is still guaranteed to be (easily)
     * winnable.
     */
    protected void setupWonWithGhost() {
        setupStarted("/player_distant_ghost_map.txt");
        getGame().move(getPlayer(), Direction.EAST);
        getGame().move(getPlayer(), Direction.EAST);
    
        assert !getGame().isInProgress();
        assert getPlayer().isAlive();
    }
    
    /**
     * Tests that we cannot go from the lost state to the started state.
     */
    @Test
    void lostThenStarted() {
        setupLost();
        
        getGame().start();
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Tests that we stay in the lost state when the stop button is pressed.
     */
    @Test
    void lostThenStopped() {
        setupLost();
        
        getGame().stop();
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Tests that we cannot go from the lost state to the won state by trying to eat the last
     * pellet.
     */
    @Test
    void lostThenEatLastPellet() {
        setupLost();
    
        for (int i = 0; i < MOVES_PLAYER_TO_PELLET + 1; i++) {
            getGame().move(getPlayer(), Direction.EAST);
        }
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Tests that we cannot go from the lost state to the started state by trying to eat a pellet.
     */
    @Test
    void lostThenEatPellet() {
        setupLost();
        
        for (int i = 0; i < MOVES_PLAYER_TO_PELLET; i++) {
            getGame().move(getPlayer(), Direction.EAST);
        }
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Tests that we cannot go from the lost state to the started state by trying to move into a
     * wall.
     */
    @Test
    void lostThenMoveIntoWall() {
        setupLost();
        
        for (int i = 0; i < MOVES_PLAYER_TO_WALL; i++) {
            getGame().move(getPlayer(), Direction.EAST);
        }
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Tests that we cannot go from the lost state to the started state by trying to move to an
     * empty square.
     */
    @Test
    void lostThenMoveIntoEmptySquare() {
        setupLost();
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isFalse();
        verify(levelObserver).levelLost();
        verify(levelObserver, times(0)).levelWon();
    }
    
    /**
     * Sets up a lost game.
     */
    protected void setupLost() {
        setupStarted("/player_distant_ghost_map.txt");
        for (int i = 0; i < MOVES_PLAYER_TO_GHOST; i++) {
            getGame().move(getPlayer(), Direction.WEST);
        }
        
        assert !getGame().isInProgress();
        assert !getPlayer().isAlive();
    }
    
    /**
     * Tests that we can go from the paused state to the started state.
     */
    @Test
    void stoppedThenStarted() {
        setupStopped(null);
        
        getGame().start();
        
        assertThat(getGame().isInProgress()).isTrue();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we stay in the paused state when we press the stop button.
     */
    @Test
    void stoppedThenStopped() {
        setupStopped(null);
        
        getGame().stop();
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the paused state to the won state by trying to eat the last
     * pellet.
     */
    @Test
    void stoppedThenEatLastPellet() {
        setupStopped("/player_empty_pellet_map.txt");
        
        getGame().move(getPlayer(), Direction.WEST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the paused state to the started state by trying to eat a pellet.
     */
    @Test
    void stoppedThenEatPellet() {
        setupStopped("/player_2_pellets_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the paused state to the lost state.
     */
    @Test
    void stoppedThenMoveIntoGhost() {
        setupStopped("/player_ghost_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the paused state to the started state by trying to move into a
     * wall.
     */
    @Test
    void stoppedThenMoveIntoWall() {
        setupStopped("/player_wall_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Tests that we cannot go from the paused state to the started state by trying to move into an
     * empty square.
     */
    @Test
    void stoppedThenMoveIntoEmptySquare() {
        setupStopped("/player_empty_pellet_map.txt");
        
        getGame().move(getPlayer(), Direction.EAST);
        
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getPlayer().isAlive()).isTrue();
        verifyZeroInteractions(levelObserver);
    }
    
    /**
     * Sets up a paused game with a given map.
     * @param mapName The resource filename of the map to be used.
     */
    protected void setupStopped(String mapName) {
        setupStarted(mapName);
        getGame().stop();
        
        assert !getGame().isInProgress();
    }
    
    /**
     * Returns the launcher of the game, needs to be set first.
     * @return The launcher instance.
     */
    protected Launcher getLauncher() {
        return launcher;
    }
    
    /**
     * Sets the launcher of the game.
     * @param launcher The launcher instance.
     */
    protected void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }
    
    /**
     * Gets the mocked level observer.
     * @return The mocked level observer.
     */
    protected Level.LevelObserver getLevelObserver() {
        return levelObserver;
    }
    
    /**
     * Gets the game from the launcher.
     * @return The game instance.
     */
    protected Game getGame() {
        return launcher.getGame();
    }
    
    /**
     * Gets the first player from the game.
     * @return The player instance.
     */
    protected Player getPlayer() {
        assert !getGame().getPlayers().isEmpty();
        
        return getGame().getPlayers().get(0);
    }
}
