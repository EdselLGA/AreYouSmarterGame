package core;
import player.Player;

public class ScoreEntry {
    private String name;
    private int score;

    public ScoreEntry(Player player){
        this.name = player.getName();
        this.score = player.getWinnings();
    }

    public ScoreEntry(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }
    public int getScore(){
        return score;
    }

}
