package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates and launches the UI with a multi level game.
 */
public class MultiLevelLauncher extends Launcher {
    
    private static final int NUMBER_OF_LEVELS = 4;

    private MultiLevelGame multiGame;
    
    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }
    
    @Override
    public MultiLevelGame makeGame() {
        Player player = getPlayerFactory().createPacMan();
        List<Level> levels = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
            levels.add(makeLevel());
        }
        
        multiGame = new MultiLevelGame(player, levels, loadPointCalculator());
        return multiGame;
    }
    
    private PointCalculator loadPointCalculator() {
        return new PointCalculatorLoader().load();
    }
}
