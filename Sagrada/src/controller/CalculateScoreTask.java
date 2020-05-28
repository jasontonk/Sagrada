package controller;

import javafx.concurrent.Task;
import model.Player;

public class CalculateScoreTask extends Task<Integer> {
    private Player player;
    
 
    public CalculateScoreTask(Player player) {
        this.player = player;
    }
    

    @Override
    protected Integer call() throws Exception {
        int score = player.calculateScore();
        return new Integer(score);
    }
}
