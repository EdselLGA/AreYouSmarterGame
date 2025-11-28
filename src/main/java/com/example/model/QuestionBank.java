package com.example.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QuestionBank{
    private Map<Category, List<Question>> questionsByCategory;
    private Set<Question> askedQuestions;

    public QuestionBank(){
        questionsByCategory = new HashMap<>();
        askedQuestions = new HashSet<>();
        try {
            loadQuestions();
        } catch (Exception e) {
            System.err.println("Error initializing QuestionBank: " + e.getMessage());
            e.printStackTrace();
            // Continue with empty question bank - will fail when trying to get questions
        }
    }

    public Question getQuestion(Category category){
        List<Question> questions = questionsByCategory.get(category);
        if (questions == null || questions.isEmpty()) {
            throw new IllegalStateException("No questions available for category: " + category);
        }
        
        List<Question> availableQuestions = questions.stream()
            .filter(q -> !askedQuestions.contains(q))
            .toList();
        if(availableQuestions.isEmpty()){
            throw new IllegalStateException("No more questions available in this category");
        }

        Question selected = availableQuestions.get(new Random().nextInt(availableQuestions.size()));
        askedQuestions.add(selected);
        return selected;
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

        private void loadQuestions() {
        try {
            // Read JSON file from resources
            java.io.InputStream inputStream = getClass().getClassLoader().getResource("questions.json").openStream();
            if (inputStream == null) {
                System.out.println("ARE YOU HERE?");
                // Fallback: try assets folder
                String fileName = "/questions.json";
                String json = new String(Files.readAllBytes(Paths.get(fileName)));
                //Files.readAllBytes(Paths.get(fileName))
                parseQuestions(json);
                return;
            }
            String json = new String(inputStream.readAllBytes());
            inputStream.close();
            parseQuestions(json);

        } catch (IOException e) {
            System.err.println("Error loading questions: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void parseQuestions(String json) {
        // Parse with GSON
        Gson gson = new Gson();
        Map<String, List<QuestionItem>> categoriesJson = gson.fromJson(
                json,
                new TypeToken<Map<String, List<QuestionItem>>>(){}.getType()
        );

        // Transform to Question objects and organize by category
        for (Map.Entry<String, List<QuestionItem>> entry : categoriesJson.entrySet()) {
            String categoryName = entry.getKey();
            Category category = mapToCategory(categoryName);

            List<Question> questions = new ArrayList<>();

            for (QuestionItem item : entry.getValue()) {
                boolean isMultipleChoice = "multiple".equals(item.getType());

                String[] options;
                if (isMultipleChoice) {
                    options = item.getChoices().toArray(new String[0]);
                } else {
                    // True/False questions
                    options = new String[]{"True", "False"};
                }

                questions.add(new Question(
                        item.getQuestion(),
                        options,
                        item.getAnswer(),
                        category,
                        isMultipleChoice
                ));
            }

            questionsByCategory.put(category, questions);
        }
    }
    private Category mapToCategory(String categoryName) {
        switch (categoryName) {
            case "History":
                return Category.HISTORY;
            case "GuessTheParadigm":
                return Category.GUESS_THE_PARADIGM;
            case "TrueOrFalse":
                return Category.TRUE_OR_FALSE;
            case "CodeSnippets":
                return Category.CODE_SNIPPETS;
            default:
                throw new IllegalArgumentException("Unknown category: " + categoryName);
        }
    }

}