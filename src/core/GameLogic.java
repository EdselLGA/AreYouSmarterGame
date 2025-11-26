// Handles scoring, winnings, progression
package core;


public class GameLogic {
    private GameModel model;
    private boolean isOver;

    public GameLogic(GameModel model){
        this.model = model;
        this.isOver = false;

    }

    
    public void startGame(){
        System.out.println("HELLO WORLD");
        model.reset();
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

    public void getQuestion(){
        if (model.canUseCategory(model.getCurrentCategory())){
            model.setCurrentQuestion(model.getQuestionBank().getQuestion(model.getCurrentCategory()));
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
    public boolean validateQuestion(int index){
        boolean answer = model.getCurrentQuestion().isCorrect(index);
        if(answer){
            System.out.println("correct Answer");
            model.incrementScore(100);
            
            // proceed to next question
            // show category if not yet
            // show helper if need new helper
        }
        else{
            System.out.println("Wrong Answer");
            // show wrong game screen
            // use save lifeline if not used
            if(!model.isSaveUsed()){
                //use save
            }
            else{
                //end game
            }
        }
        model.updateHelperUsage();
        model.incrementCategoryUse(model.getCurrentCategory());
        return answer;
    }

    public void showResults(){

    }


}