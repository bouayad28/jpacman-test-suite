package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * A test class for the PlayerCollisions class. It was created to use a parallel class
 * hierarchy structure together with DefaultPlayerInteractionMap and CollisionMap.
 */
class PlayerCollisionsTest extends CollisionMapTest {

    /**
     * A setup method called before each test instance where we
     * set the PlayerCollision.
     */
    @BeforeEach
    void setupDefaultPlayerInteractionMap() {
        setCollisionMap(new PlayerCollisions(getPointCalculator()));
    }

    /**
     * Helper method to obtain the casted CollisionMap from the superclass.
     * @return
     */
    @Override
    protected PlayerCollisions getCollisionMap() {
        return (PlayerCollisions) super.getCollisionMap();
    }

    /**
     * A test to check whether a player and ghost collision leads to
     * the correct outcome.
     */
    @Test
    void playerVersusGhostTest() {
        getCollisionMap().playerVersusGhost(getPlayer(), getGhost());
        Mockito.verify(getPointCalculator()).collidedWithAGhost(getPlayer(), getGhost());
        Mockito.verify(getPlayer()).setAlive(false);
        Mockito.verify(getPlayer()).setKiller(getGhost());
    }

    /**
     * A test to check whether a player and pellet collision leads to
     * the correct outcome.
     */
    @Test
    void playerVersusPelletTest() {
        getCollisionMap().playerVersusPellet(getPlayer(), getPellet());
        Mockito.verify(getPellet()).leaveSquare();
        Mockito.verify(getPointCalculator()).consumedAPellet(getPlayer(), getPellet());
    }
}
