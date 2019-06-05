package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * A test class for the CollisionMap class. It was created to use a parallel class
 * hierarchy for the DefaulPlayerInteractionMap and the PlayerCollisions tests.
 */
abstract class CollisionMapTest {

    /**
     * The private Player attribute.
     */
    private Player player;
    /**
     * The private Ghost attribute.
     */
    private Ghost ghost;
    /**
     * The private Pellet attribute.
     */
    private Pellet pellet;
    /**
     * The private Point calculator attribute.
     */
    private PointCalculator pointCalculator;
    /**
     * The private Collision map attribute.
     */
    private CollisionMap collisionMap;

    /**
     * Gets the private Player attribute.
     *
     * @return the player
     */
    protected Player getPlayer() {
        return player;
    }

    /**
     * Gets the private Ghost attribute.
     *
     * @return the ghost
     */
    protected Ghost getGhost() {
        return ghost;
    }

    /**
     * Gets the private Pellet attribute.
     *
     * @return the pellet
     */
    protected Pellet getPellet() {
        return pellet;
    }

    /**
     * Gets the private PointCalculator attribute.
     *
     * @return the point calculator
     */
    protected PointCalculator getPointCalculator() {
        return pointCalculator;
    }

    /**
     * Gets the private CollisionMap attribute.
     *
     * @return the collision map
     */
    protected CollisionMap getCollisionMap() {
        return collisionMap;
    }

    /**
     * Sets the private CollisionMap attribute .
     *
     * @param collisionMap collisionMap
     *
     */
    public void setCollisionMap(CollisionMap collisionMap) {
        this.collisionMap = collisionMap;
    }

    /**
     * This is a setup method that mocks a player, ghost, pellet
     * and a pointCalculator once before each test.
     */
    @BeforeEach
    void setup() {
        player = Mockito.mock(Player.class);
        ghost = Mockito.mock(Ghost.class);
        pellet = Mockito.mock(Pellet.class);
        pointCalculator = Mockito.mock(PointCalculator.class);
    }

    /**
     * A test to check whether a player and ghost collision leads to
     * the correct outcome.
     */
    @Test
    void playerGhostCollision() {
        collisionMap.collide(player, ghost);
        Mockito.verify(pointCalculator).collidedWithAGhost(player, ghost);
        Mockito.verify(player).setAlive(false);
        Mockito.verify(player).setKiller(ghost);
    }

    /**
     * A test to check whether a player and pellet collision leads to
     * the correct outcome.
     */
    @Test
    void playerPelletCollision() {
        collisionMap.collide(player, pellet);
        Mockito.verify(pellet).leaveSquare();
        Mockito.verify(pointCalculator).consumedAPellet(player, pellet);
    }

    /**
     * A test to check whether a player and player collision leads to
     * the correct outcome.
     */
    @Test
    void playerPlayerCollision() {
        collisionMap.collide(player, player);
        verifyZeroInteractions(player);
    }

    /**
     * A test to check whether a ghost and ghost collision leads to
     * the correct outcome.
     */
    @Test
    void ghostGhostCollision() {
        collisionMap.collide(ghost, ghost);
        verifyZeroInteractions(ghost);
    }

    /**
     * A test to check whether a ghost and pellet collision leads to
     * the correct outcome.
     */
    @Test
    void ghostPelletCollision() {
        collisionMap.collide(ghost, pellet);
        verifyZeroInteractions(ghost);
    }

    /**
     * A test to check whether a ghost and player collision leads to
     * the correct outcome.
     */
    @Test
    void ghostPlayerCollision() {
        collisionMap.collide(ghost, player);
        Mockito.verify(pointCalculator).collidedWithAGhost(player, ghost);
        Mockito.verify(player).setAlive(false);
        Mockito.verify(player).setKiller(ghost);
    }

    /**
     * A test to check whether a pellet and ghost collision leads to
     * the correct outcome.
     */
    @Test
    void pelletGhostCollision() {
        collisionMap.collide(pellet, ghost);
        verifyZeroInteractions(pellet);
    }

    /**
     * A test to check whether a pellet and pellet collision leads to
     * the correct outcome.
     */
    @Test
    void pelletPelletCollision() {
        collisionMap.collide(pellet, pellet);
        verifyZeroInteractions(pellet);
    }

    /**
     * A test to check whether a pellet and player collision leads to
     * the correct outcome.
     */
    @Test
    void pelletPlayerCollision() {
        collisionMap.collide(pellet, player);
        Mockito.verify(pellet).leaveSquare();
        Mockito.verify(pointCalculator).consumedAPellet(player, pellet);
    }

    /**
     * A test to check whether an invalid unit and player collision leads to
     * the correct outcome.
     */
    @Test
    void colliderHasNoHandlerTest() {
        OtherUnit otherUnit = new OtherUnit();
        collisionMap.collide(otherUnit, player);

        verifyZeroInteractions(pointCalculator);
    }

    /**
     * A test to check whether a player and invalid unit collision leads to
     * the correct outcome.
     */
    @Test
    void collideeHasNoHandlerTest() {
        OtherUnit otherUnit = new OtherUnit();
        collisionMap.collide(player, otherUnit);

        verifyZeroInteractions(pointCalculator);
    }

    /**
     * Helper method for test cases where we need and invalid unit.
     */
    private static class OtherUnit extends Unit {

        @Override
        public Sprite getSprite() {
            return null;
        }
    }
}
