package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

/**
 * A test class for the MapParser class. The focus here was on testing
 * the interaction of the MapParser with the two
 * factories it depends on, levelFactory and boardFactory.
 * For this reason, the factories were mocked.
 */
class MapParserTest {

    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private MapParser mapParser;

    /**
     * This is a setup method that mocks a levelFactory
     * and a boardFactory for a mapParser once before each test.
     */
    @BeforeEach
    void setup() {
        levelFactory = Mockito.mock(LevelFactory.class);
        boardFactory = Mockito.mock(BoardFactory.class);
        mapParser = new MapParser(levelFactory, boardFactory);
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array
     * leads to a call of the levelFactory.createLevel() method.
     */
    @Test
    void testLevelCreationViaCharArray() {
        char[][] mapArray = new char[][]{{'#'}};
        mapParser.parseMap(mapArray);

        Mockito.verify(levelFactory).createLevel(any(), any(), any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of a string list leads
     * to a call of the levelFactory.createLevel() method.
     */
    @Test
    void testLevelCreationViaStringList() {
        List<String> mapList = new ArrayList<>();
        mapList.add("#");
        mapParser.parseMap(mapList);

        Mockito.verify(levelFactory).createLevel(any(), any(), any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of an input stream leads to
     * a call of the levelFactory.createLevel() method.
     */
    @Test
    void testLevelCreationViaInputStream() {
        try {
            mapParser.parseMap(new ByteArrayInputStream("#".getBytes(StandardCharsets.UTF_8)));
        } catch (IOException ioe) {
            fail();
        }

        Mockito.verify(levelFactory).createLevel(any(), any(), any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of a file leads to
     * a call of the levelFactory.createLevel() method.
     */
    @Test
    void testLevelCreationViaFile() {
        try {
            mapParser.parseMap("/only_wall_map.txt");
        } catch (IOException ioe) {
            fail();
        }

        Mockito.verify(levelFactory).createLevel(any(), any(), any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array
     * leads to a call of the boardFactory.createBoard() method.
     */
    @Test
    void testBoardCreationViaCharArray() {
        char[][] mapArray = new char[][]{{'#'}};
        mapParser.parseMap(mapArray);

        Mockito.verify(boardFactory).createBoard(any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of a string list leads
     * to a call of the boardFactory.createBoard() method.
     */
    @Test
    void testBoardCreationViaStringList() {
        List<String> mapList = new ArrayList<>();
        mapList.add("#");
        mapParser.parseMap(mapList);

        Mockito.verify(boardFactory).createBoard(any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of an input stream leads to
     * a call of the boardFactory.createBoard() method.
     */
    @Test
    void testBoardCreationViaInputStream() {
        try {
            mapParser.parseMap(new ByteArrayInputStream("#".getBytes(StandardCharsets.UTF_8)));
        } catch (IOException ioe) {
            fail();
        }

        Mockito.verify(boardFactory).createBoard(any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of a file leads to
     * a call of the boardFactory.createBoard() method.
     */
    @Test
    void testBoardCreationViaFile() {
        try {
            mapParser.parseMap("/only_wall_map.txt");
        } catch (IOException ioe) {
            fail();
        }

        Mockito.verify(boardFactory).createBoard(any());
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array with a "#"
     * leads to a call of the boardFactory.creatWall() method.
     */
    @Test
    void testWallCreation() {
        char[][] mapArray = new char[][]{{'#'}};
        mapParser.parseMap(mapArray);

        Mockito.verify(boardFactory).createWall();
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array with a ' '
     * leads to a call of the boardFactory.createGround() method.
     */
    @Test
    void testGroundCreation() {
        char[][] mapArray = new char[][]{{' '}};
        mapParser.parseMap(mapArray);

        Mockito.verify(boardFactory).createGround();
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array with a 'G'
     * leads to a call of the boardFactory.createGround() and the
     * levelFactory).createGhost() methods.
     */
    @Test
    void testGhostCreation() {
        Ghost ghost = Mockito.mock(Ghost.class);
        Mockito.when(levelFactory.createGhost()).thenReturn(ghost);

        char[][] mapArray = new char[][]{{'G'}};
        mapParser.parseMap(mapArray);

        Mockito.verify(levelFactory).createGhost();
        Mockito.verify(boardFactory).createGround();
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array with a '.'
     * leads to a call of the boardFactory.createGround() and the
     * levelFactory).createPellet() methods.
     */
    @Test
    void testPelletCreation() {
        Pellet pellet = Mockito.mock(Pellet.class);
        Mockito.when(levelFactory.createPellet()).thenReturn(pellet);

        char[][] mapArray = new char[][]{{'.'}};
        mapParser.parseMap(mapArray);

        Mockito.verify(levelFactory).createPellet();
        Mockito.verify(boardFactory).createGround();
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array with a 'P'
     * leads to a call of the boardFactory.createGround() method.
     */
    @Test
    void testPlayerCreation() {
        char[][] mapArray = new char[][]{{'P'}};
        mapParser.parseMap(mapArray);

        Mockito.verify(boardFactory).createGround();
    }

    /**
     * Tests whether or not mapParser.parseMap() of a char array with an invalid character
     * throws a PacmanConfigurationException.
     */
    @Test
    void testInvalidCharacter() {
        char[][] mapArray = new char[][]{{'L'}};
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(mapArray));
    }

    /**
     * Tests whether or not mapParser.parseMap() of a string list that is null
     * throws a PacmanConfigurationException.
     */
    @Test
    void stringListIsNullTest() {
        List<String> stringList = null;
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(stringList));
    }

    /**
     * Tests whether or not mapParser.parseMap() of an empty string list
     * throws a PacmanConfigurationException.
     */
    @Test
    void emptyStringListTest() {
        List<String> stringList = new ArrayList<>();
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(stringList));
    }

    /**
     * Tests whether or not mapParser.parseMap() of a string list with an empty entry
     * throws a PacmanConfigurationException.
     */
    @Test
    void emptyStringInListTest() {
        List<String> stringList = new ArrayList<>();
        stringList.add("");
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(stringList));
    }

    /**
     * Tests whether or not mapParser.parseMap() of a string list with a entries of
     * variable length throws a PacmanConfigurationException.
     */
    @Test
    void varyingStringLengthInListTest() {
        List<String> stringList = new ArrayList<>();
        stringList.add("##");
        stringList.add("#");
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(stringList));
    }

    /**
     * Tests whether or not mapParser.parseMap() of a nonexistent file
     * throws a PacmanConfigurationException.
     */
    @Test
    void nonExistentMapFile() {
        assertThrows(PacmanConfigurationException.class, () ->
            mapParser.parseMap("/non_existing_map.txt"));
    }
}
