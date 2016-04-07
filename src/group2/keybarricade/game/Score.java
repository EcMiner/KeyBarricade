package group2.keybarricade.game;

/**
 * This class is used to keep track of the steps and time it takes for the
 * player to move
 */
public class Score {

    private final long beginTime;
    private int steps;

    public Score() {
        // Set begin time to current system time in milliseconds
        beginTime = System.currentTimeMillis();
        // Set begni value of steps to 0
        steps = 0;
    }

    /**
     * add one to steps
     */
    public void addStep() {
        steps += 1;
    }

    /**
     *
     * @return amount of steps.
     */
    public int getSteps() {
        return steps;
    }

    /**
     *
     * @return duration of the game in seconds.
     */
    public long getDurationSeconds() {
        /*
         calulate the duration by doing ((double) (Current System time in ms - beginTime) / 1000.0).
         current systemtime is cast from long to double.
         */
        return Math.round((double) (System.currentTimeMillis() - beginTime) / 1000d);
    }
}
