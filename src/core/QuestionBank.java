// Stores questions by category
package core;

import java.io.FileReader;
import java.lang.classfile.instruction.DiscontinuedInstruction.JsrInstruction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class QuestionBank{
    private Map<Category, List<Question>> questionsByCategory;
    private Set<Question> askedQuestions;

    public QuestionBank(){
        questionsByCategory = new HashMap<>();
        askedQuestions = new HashSet<>();
        loadQuestions();
    }

    public Question getQuestion(Category category){
        List<Question> questions = questionsByCategory.get(category);
        List<Question> availableQuestions = questions.stream()
            .filter(q -> !askedQuestions.contains(q))
            .toList();
        if(!availableQuestions.isEmpty()){
            throw new IllegalStateException("No more questions available in this category");
        }

        Question selected = availableQuestions.get(new Random().nextInt(availableQuestions.size()));
        askedQuestions.add(selected);
        return selected; // No more questions available in this category
    }

    public Question getFinalQuestion(){
        List<Question> allQuestions = questionsByCategory.values().stream()
            .flatMap(List::stream)
            .filter(q -> !askedQuestions.contains(q))
            .toList();
        if(allQuestions.isEmpty()){
            throw new IllegalStateException("No more questions available");
        }

        Question selected = allQuestions.get(new Random().nextInt(allQuestions.size()));
        askedQuestions.add(selected);
        return selected;
    }

    public void reset(){
        askedQuestions.clear();
    }

    private void loadQuestions(){ //Load Questions from JSON file
        // Implementation to load questions from a JSON file into questionsByCategory map
        // This is a placeholder for actual loading logic

        String fileName = "questions.json";
//        try ( JsonReader reader = Json.createReader(new FileReader(fileName)) ){
//            
//       }
    }
}