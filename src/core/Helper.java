package core;

import java.util.Random;

public class Helper {
    private String name;
    private int accuracyPercentage;
    private Random random;
    public Helper(String name, int accuracyPercentage) {
        this.name = name;
        this.accuracyPercentage = accuracyPercentage;
        this.random = new Random();
    }
    public String getName() {
        return name;
    }
    public boolean willAnswerCorrectly() {
        int roll = random.nextInt(100) + 1; // 1 to 100
        return roll <= accuracyPercentage;
    }

    public int suggestAnswerIndex(String[] options, int correctIndex) {
        if (willAnswerCorrectly()) {
            return correctIndex;
        } else {
            int suggestedIndex;
            do {
                suggestedIndex = random.nextInt(options.length);
            } while (suggestedIndex == correctIndex);
            return suggestedIndex;
        }
    }
}
