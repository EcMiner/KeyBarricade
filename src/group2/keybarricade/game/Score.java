package group2.keybarricade.game;

public class Score {
    private long beginTime;
    private int steps;
    
    public Score() {
        beginTime = System.currentTimeMillis();
        steps = 0;
    }
    
    public void addStep() {
        steps += 1;
    }
    
    public int getSteps() {
        return steps;
    }
    
    public long getDurationSeconds() {
        return Math.round((double) (System.currentTimeMillis() - beginTime) / 1000d);
    }
}
