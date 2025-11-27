// Handles scoring, winnings, progression
package core;

import java.util.Arrays;


public class GameLogic {
    private GameModel model;
    private boolean isOver;

    public GameLogic(GameModel model){
        this.model = model;
        this.isOver = false;

    }

    public NextStep lockAnswer(){
        boolean answer = model.getCurrentQuestion().isCorrect(model.getQuestionChoiceIndex());
        if(answer){
            model.incrementScore(100);
            model.incrementQuestionCounters();
            model.incrementCategoryUse(model.getCurrentCategory());
            model.updateHelperUsage();
            System.out.println(Arrays.toString(model.getHelperUsage()));
            if(model.getQuestionNumber()==11){
                return NextStep.LAST_QUESTION;
            }
            if (!model.canUseCurrentHelper(model.getCurrentHelperIndex())){
                return NextStep.PICK_HELPER;
            }
            if (!model.canUseCategory(model.getCurrentCategory())){
                return NextStep.PICK_CATEGORY;
            }
        }
        else{
            // use save lifeline
            if (!model.isSaveUsed()){
                // use save
                if(model.canUseLifeline()){
                    model.useSaveLifeline();
                    int helperSuggestion = model.getCurrentHelper().suggestAnswerIndex(model.getCurrentQuestion().getOptions(), model.getCurrentQuestion().getCorrectIndex());
                    model.incrementScore(100);
                    model.incrementQuestionCounters();
                    model.incrementCategoryUse(model.getCurrentCategory());
                    model.updateHelperUsage();
                    if (model.getCurrentQuestion().isCorrect(helperSuggestion)){
                        if(model.getQuestionNumber()==11){
                            return NextStep.LAST_QUESTION;
                        }
                        if (!model.canUseCurrentHelper(model.getCurrentHelperIndex())){
                            return NextStep.PICK_HELPER;
                        }
                        if (!model.canUseCategory(model.getCurrentCategory())){
                            return NextStep.PICK_CATEGORY;
                        }
                    }
                    else{
                        return NextStep.GAME_OVER;
                    }
                }
            }
            else{
                return NextStep.GAME_OVER;
            }

        }
        return NextStep.NEXT_QUESTION;
    }
    
    public void startGame(){
        System.out.println("HELLO WORLD");
        model.reset();
    }



    public void onDropOut(){
        
    }

    public void selectHelper(int index){
        // Selecting a helper
        model.setHelper(index);
    }

    public void selectCategory(int index){
        model.setCurrentCategory(index);
    }

    public void nextQuestion(){
        // if helper == 2
        // if category == 2
        // getQuestion
    }

    public void loadQuestion(){
        if (model.getCurrentCategory()!=null){
            if (model.canUseCategory(model.getCurrentCategory())){
            model.setCurrentQuestion(model.getQuestionBank().getQuestion(model.getCurrentCategory()));
            }
        }
    }

    public void getQuestionTest(){
        model.setCurrentCategory(0);
        model.setHelper(0);
        if (model.canUseCategory(model.getCurrentCategory())){
            model.setCurrentQuestion(model.getQuestionBank().getQuestion(model.getCurrentCategory()));
        }
        System.out.println(model.getCurrentQuestion());
        System.out.println(model.getCurrentQuestion().getCorrectAnswer());
        System.out.println(model.getCurrentQuestion().getQuestionText());
    }

    public Question showQuestion(){
        //model.getQuestionBank().getQuestion(model.currentCategory);
        return model.getCurrentQuestion();
    }
    

    public void showResults(){

    }


}