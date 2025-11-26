package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HighScoreRepository {
    private List<ScoreEntry> highscores;

    public HighScoreRepository(){
        this.highscores = new ArrayList<>();
        loadHighScores();
    }

    public void loadHighScores(){
        highscores = getHighscores();
    }

    public List<ScoreEntry> getHighscores(){
        String fileName = "highscores.json";
        List<ScoreEntry> hs = new ArrayList<ScoreEntry>();

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
