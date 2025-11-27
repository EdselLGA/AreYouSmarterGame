// Handles scoring, winnings, progression
package core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import player.Player;

public class GameModel {
    // Implementation of game logic goes here
    private Player player;
    private int totalWinnings;

    //Question
    private Question currentQuestion;
    private int questionNumber;
    private int questionChoiceIndex;

    // Category
    private Map<Category, Integer> categoryUseCount;
    private Category currentCategory;

    //Helper
    private Helper[] helpers;
    private int[] helperUsage;
    private Helper currentHelper;
    private int currentHelperIndex;

    //Misc
    private QuestionBank questionBank;

    public GameModel(){
        this.questionBank = new QuestionBank();
        reset();
    }

    public void reset(){
        questionNumber = 1;
        totalWinnings = 0;
        player = new Player("");
        if(player != null){
            player.resetLifelines();
        }
        initializeHelpers();
        initializeCategories();
        currentHelper = null;
        currentQuestion = null;
        currentCategory = null;
        questionChoiceIndex = -1;
        questionBank.reset();
    }

    public void initializeCategories(){
        categoryUseCount = new HashMap<>();
        categoryUseCount.put(Category.HISTORY,1);
        categoryUseCount.put(Category.GUESS_THE_PARADIGM,1);
        categoryUseCount.put(Category.TRUE_OR_FALSE,1);
        categoryUseCount.put(Category.CODE_SNIPPETS,1);
    }
    // HELPERS
    public void initializeHelpers(){
        this.helpers = new Helper[5];
        this.helperUsage = new int[5];
        for(int i=0; i< 5; i++){
            helpers[i] = new Helper(String.valueOf(i+1), (i+1)*20);
            helperUsage[i] = 0;
        }
    }
    public int[] getHelperUsage(){
        return helperUsage;
    }
    public Helper getCurrentHelper(){
        return currentHelper;
    }
    public int getCurrentHelperIndex(){
        return currentHelperIndex;
    }
    public void setHelper(int index){
        currentHelperIndex = index;
        currentHelper = helpers[index];
    }
    public void updateHelperUsage(){
        helperUsage[currentHelperIndex] = helperUsage[currentHelperIndex] + 1;
    }
    // HELPERS

    public void incrementQuestionCounters(){
        questionNumber++;
        //questionsWithCurrentHelper++;
    }
    
    public void incrementCategoryUse(Category category){
        categoryUseCount.put(category, categoryUseCount.get(category) + 1);
    }
    public boolean canUseCategory(Category category){
        return categoryUseCount.get(category) <= 2;
    }
    public boolean canUseCurrentHelper(int index){
        return helperUsage[currentHelperIndex] < 2;
    }
    public Category getCurrentCategory(){
        return currentCategory;
    }
    public void setCurrentCategory(int index){
        switch (index) {
            case 0:
                currentCategory = Category.HISTORY;
                break;
            case 1:
                currentCategory = Category.GUESS_THE_PARADIGM;
                break;
            case 2:
                currentCategory = Category.TRUE_OR_FALSE;
                break;
            case 3:
                currentCategory = Category.CODE_SNIPPETS;
                break;
            default:
                throw new IllegalArgumentException("Unknown category: " + index);
        }
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

    // Questions
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
    public int getQuestionChoiceIndex(){
        return questionChoiceIndex;
    }
    public void setQuestionChoiceIndex(int index){
        this.questionChoiceIndex = index;
    }
    // Questions

    public int getTotalWinnings(){
        return totalWinnings;
    }

    public Map<Category, Integer> getCategoryUseCount(){
        return categoryUseCount;
    }

    // Lifelines
    public boolean isPeekUsed(){
        return player.getPeekLifeline().isUsed();
    }
    public boolean isCopyUsed(){
        return player.getCopyLifeline().isUsed();
    }
    public boolean isSaveUsed(){
        return player.getSaveLifeline().isUsed();
    }
    public boolean canUseLifeline(){
        return questionNumber <= 10;
    }
    public void useSaveLifeline(){
        player.getSaveLifeline().useLifeline();
    }
    public void useCopyLifeline(){
        player.getCopyLifeline().useLifeline();
    }
    public void usePeekLifeline(){
        player.getPeekLifeline().useLifeline();
    }
    //Lifelines

    public QuestionBank getQuestionBank(){
        return questionBank;
    }

}