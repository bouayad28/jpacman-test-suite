package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * A test class for the start method of the Game class.
 */
public class GameUnitTest {

    private SinglePlayerGame singlePlayerGame;
    private Player player;
    private Level level;
    private PointCalculator pointCalculator;

    /**
     * A setup method called before each test instance where we
     * mock a player, level and pointCalculator and create a new
     * singlePlayerGame.
     */
    @BeforeEach
    void setup() {
        player = Mockito.mock(Player.class);
        level = Mockito.mock(Level.class);
        pointCalculator = Mockito.mock(PointCalculator.class);
        singlePlayerGame = new SinglePlayerGame(player, level, pointCalculator);
    }

    /**
     * A test to check whether the scenario where the player is alive and 1 pellet
     * remains leads to the correct outcome.
     */
    @Test
    public void playerIsAlivePelletsRemainTest() {
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(1);

        singlePlayerGame.start();
        assertTrue(singlePlayerGame.isInProgress());
    }

    /**
     * A test to check whether the scenario where the player is not alive and 1 pellet
     * remains leads to the correct outcome.
     */
    @Test
    public void playerIsNotAlivePelletsRemainTest() {
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(false);
        Mockito.when(level.remainingPellets()).thenReturn(1);

        singlePlayerGame.start();
        assertFalse(singlePlayerGame.isInProgress());
    }

    /**
     * A test to check whether the scenario where the player is alive and 0 pellet
     * remains leads to the correct outcome.
     */
    @Test
    public void playerIsAlivePelletsDoNotRemainTest() {
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(0);

        singlePlayerGame.start();
        assertFalse(singlePlayerGame.isInProgress());
    }

    /**
     * A test to check whether the scenario where the player is alive, 1 pellet
     * remains and the start method is called two times leads to the correct outcome.
     */
    @Test
    public void playerIsAlivePelletsRemainTestStartTwiceTest() {
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(1);

        singlePlayerGame.start();
        singlePlayerGame.start();

        assertTrue(singlePlayerGame.isInProgress());
    }
}
