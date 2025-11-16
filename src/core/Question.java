package core;

// Question model class
// Question is multiple choice or true/false
// Categories are history, guess the paradigm, true or false, snippets, random choice



public class Question {
    private String questionText;
    private String[] options; // For multiple choice questions
    private String correctAnswer;
    private int correctIndex;
    private Category category; // e.g., "History", "True or False", etc.
    private boolean isMultipleChoice; // True if it's a multiple choice question, false if it's true/false

    public Question(String questionText, String[] options, String correctAnswer, Category category, boolean isMultipleChoice) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.isMultipleChoice = isMultipleChoice;
        assignCorrectIndex();
    }

    public void assignCorrectIndex(){
        for (int i = 0; i < options.length; i++){
            if (options[i].equals(correctAnswer)){
                correctIndex = i;
                return;
            }
        }
    }

    public boolean isCorrect(int index){
        return index == correctIndex; 
    }

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

}