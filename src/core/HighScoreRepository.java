package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Path;

import player.Player;

public class HighScoreRepository {
    private List<ScoreEntry> highscores;

    public HighScoreRepository(){
        this.highscores = new ArrayList<>();
        loadHighScores();
    }

    public void loadHighScores(){
        highscores = getHighscores();
    }

    public void addHighScore(Player player){
        highscores.add(new ScoreEntry(player));
        highscores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
    }

    public void saveHighScores(){
        Gson gson = new Gson();
        gson.toJson(highscores);
    }

    public List<ScoreEntry> getHighscores(){
        String fileName = "assets/highscores.json";
        List<ScoreEntry> hs = new ArrayList<ScoreEntry>();

        Path path = Paths.get(fileName);
        try {
            if(!Files.exists(path)){
            Files.write(path, "[]".getBytes());
        }    
        } catch (Exception e) {
            System.err.println("Could Not Create File");
        }
                

        try {
            String json = new String(Files.readAllBytes(Paths.get(fileName)));

            Gson gson = new Gson();
            List<ScoreItem> highScoreJson = gson.fromJson(
                    json,
                    new TypeToken<List<String>>() {}.getType()
            );

            List<ScoreEntry> scoreEntrys = new ArrayList<>();

            for(ScoreItem item : highScoreJson){
                int score = item.getScore();
                String name = item.getName();
                ScoreEntry se = new ScoreEntry(name,score);
                scoreEntrys.add(se);
            }

            scoreEntrys.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
            hs = scoreEntrys;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hs;
    }

}
