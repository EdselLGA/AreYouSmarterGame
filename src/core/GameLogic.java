// Handles scoring, winnings, progression
package core;

import java.util.List;
import java.util.Map;
import player.Player;

public class GameLogic {
    // Implementation of game logic goes here
    private Player player;
    private Question currentQuestion;
    private int questionNumber;
    private int totalWinnings;

    private Map<Category, Integer> categoryUseCount;
    private QuestionBank questionBank;

    private Helper currentHelper;
    private List<Helper> helpers;
    private int questionsWithCurrentHelper; //0-2 counter
    private int helperSuggestionIndex;

    public GameLogic(){
        this.questionBank = new QuestionBank();
    }

    public void reset(){
        questionNumber = 1;
        totalWinnings = 0;
        questionsWithCurrentHelper = 0;
        player.resetLifelines(); 
        helpers = initializeHelpers();
        currentHelper = null;
        helperSuggestionIndex = -1;
        currentQuestion = null;
        questionBank.reset();
    }

    private List<Helper> initializeHelpers(){
        // Initialize helpers with different accuracy percentages
        return List.of(
            new Helper("A", 20),
            new Helper("B", 40),
            new Helper("C", 60),
            new Helper("D", 80),
            new Helper("E", 100)
        );
    }

    public boolean needsNewHelper(){
        return questionsWithCurrentHelper >= 2 &&
                questionNumber <= 10 &&
                !helpers.isEmpty();
    }
    public void assignNewHelper(Helper helper){
        currentHelper = helper;
        helpers.remove(helper);
        questionsWithCurrentHelper = 0;
    }
    public void incremenetQuestionCounters(){
        questionNumber++;
        questionsWithCurrentHelper++;
    }
    public boolean canUseLifeline(){
        return questionNumber <= 10;
    }
    public void incrementCategoryUse(Category category){
        categoryUseCount.put(category, categoryUseCount.get(category) + 1);
    }
    public boolean canUseCategory(Category category){
        return categoryUseCount.get(category) < 2;
    }
    public void incrementScore(int amount){
        totalWinnings += amount;
        player.addWinnings(amount);
    }

    public String getPlayerName(){
        return player.getName();
    }
    public void setPlayer(Player player){
        this.player = player;
    }

    public Helper getCurrentHelper(){
        return currentHelper;
    }
    public void setCurrentHelper(Helper helper){
        this.currentHelper = helper;
    }

    public List<Helper> getHelpers(){
        return helpers;
    }
    public int getHelperSuggestionIndex(){
        return helperSuggestionIndex;
    }
    public void setHelperSuggestionIndex(int index){
        this.helperSuggestionIndex = index;
    }

    public Question getCurrentQuestion(){
        return currentQuestion;
    }
    public void setCurrentQuestion(Question question){
        this.currentQuestion = question;
    }

    public int getQuestionNumber(){
        return questionNumber;
    }
    public int setQuestionNumber(int number){
        return questionNumber = number;
    }

    public int getTotalWinnings(){
        return totalWinnings;
    }

    public Map<Category, Integer> getCategoryUseCount(){
        return categoryUseCount;
    }

    public boolean isPeekUsed(){
        return player.getPeekLifeline().isUsed();
    }
    public boolean isCopyUsed(){
        return player.getCopyLifeline().isUsed();
    }
    public boolean isSaveUsed(){
        return player.getSaveLifeline().isUsed();
    }

    public QuestionBank getQuestionBank(){
        return questionBank;
    }

}