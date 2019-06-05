package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

/**
 * A test class for the DefaultPlayerInteractionMap class. It was created to use a parallel class
 * hierarchy structure together with PlayerCollisions and CollisionMap.
 */
class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    /**
     * A setup method called before each test instance where we
     * set the defaultPlayerInteractionMap.
     */
    @BeforeEach
    void setupDefaultPlayerInteractionMap() {
        setCollisionMap(new DefaultPlayerInteractionMap(getPointCalculator()));
    }
}
