package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;

/**
 * Implementation of a Pacman game where when a level is won, another can be played and the score
 * is retained.
 */
public class MultiLevelGame extends Game {
    
    private final Player player;
    private final List<Level> levels;
    private final Object progressLock = new Object();
    
    private Level level;
    private boolean inProgress;
    private int levelNumber = 0;
    
    /**
     * Creates a new multi level game.
     * @param player The player.
     * @param levels A list of all levels that will be played in order. Must contain at least one
     *               level.
     * @param pointCalculator The point calculator used to compute the score.
     */
    public MultiLevelGame(Player player, List<Level> levels, PointCalculator pointCalculator) {
        super(pointCalculator);
    
        assert player != null;
        assert levels != null;
        assert !levels.isEmpty();
    
        this.player = player;
        this.levels = levels;
        
        this.level = levels.get(0);
        this.level.registerPlayer(player);
        this.inProgress = false;
    }
    
    @Override
    public void start() {
        synchronized (progressLock) {
            // Already running
            if (isInProgress()) {
                return;
            }
            
            // First start and unpause
            if (getLevel().isAnyPlayerAlive() && getLevel().remainingPellets() > 0) {
                inProgress = true;
                getLevel().addObserver(this);
                getLevel().start();
                return;
            }
            
            // Continue to next level
            if (levelNumber < levels.size() - 1
                && getLevel().remainingPellets() == 0
                && getLevel().isAnyPlayerAlive()) {
                levelNumber++;
                level = levels.get(levelNumber);
                level.registerPlayer(player);
                inProgress = true;
                getLevel().addObserver(this);
                getLevel().start();
            }
        }
    }
    
    @Override
    public void stop() {
        synchronized (progressLock) {
            // Already paused or ended
            if (!isInProgress()) {
                return;
            }
            
            inProgress = false;
            getLevel().stop();
        }
    }
    
    @Override
    public boolean isInProgress() {
        return inProgress;
    }
    
    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }
    
    @Override
    public Level getLevel() {
        return level;
    }
    
    /**
     * Get the number of the level that is currently being played. If a level was won or lost it
     * will still return the number of that level.
     * @return The number of the level.
     */
    public int getLevelNumber() {
        return this.levelNumber;
    }
}
