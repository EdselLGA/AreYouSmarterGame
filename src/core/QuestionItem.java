package core;

import java.util.List;

class QuestionItem {
    private int id;
    private String question;
    private List<String> choices;  // Null for true/false questions
    private String answer;
    private String difficulty;
    private String type;

    public String getQuestion() { return question; }
    public List<String> getChoices() { return choices; }
    public String getAnswer() { return answer; }
    public String getType() { return type; }
}