package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        FileSelector fileSelector = new FileSelector();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("questions/" + fileSelector.selectFile()));
        List<String> input = new ArrayList<>();

        //I prefer to load every line in the file into a list first, and then read that list
        while (true){
            String readLine = bufferedReader.readLine();
            if(readLine == null)
                break;
            input.add(readLine);
        }

        RevisionTool revisionTool = new RevisionTool(input);
        revisionTool.selectMode();
        revisionTool.startStudySession();
    }

    public static void printLine(){
        System.out.println("-----------------------------------------------------------------------------------------");
    }
}

class FileSelector{
    Scanner scanner = new Scanner(System.in);

    public String selectFile() throws IOException {
        Main.printLine();
        System.out.println("Files found in questions folder:");
        List<String> directoryList = Files.list(Paths.get("questions")).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
        directoryList.forEach(System.out::println);
        Main.printLine();

        System.out.println("Select a file to load (type the name as shown above):");
        String userInput = scanner.nextLine();
        while(!directoryList.contains(userInput)){
            System.out.println("File not found.");
            userInput = scanner.nextLine();
        }
        return userInput;
    }
}

class RevisionTool{
    private final List<Question> questionList;
    private final int numberOfQuestions;
    private final List<Question> incorrectQuestions;
    private final Scanner scanner;
    private boolean useLooseMarking;

    public RevisionTool(List<String> input){
        questionList = new ArrayList<>();
        incorrectQuestions = new ArrayList<>();
        scanner = new Scanner(System.in);

        //Load questions
        Main.printLine();
        System.out.println("Loading questions...");
        for (String string : input) {
            String[] strings = string.split("\\|");
            if(isQuestionValid(strings))
                questionList.add(new Question(strings));
        }
        numberOfQuestions = questionList.size();
        System.out.printf("Initialized %d questions.\n", numberOfQuestions);
        Main.printLine();
    }

    void selectMode(){
        System.out.println("Use loose marking? If yes, only keywords will be checked. (Y/N)");
        String userInput = scanner.nextLine();
        while (!userInput.equals("Y") && !userInput.equals("N")){
            System.out.println("Invalid input. Please type Y for yes and N for no.");
            userInput = scanner.nextLine();
        }
        if(userInput.equals("Y"))
            useLooseMarking = true;
        Main.printLine();
    }

    void startStudySession(){
        while(true){
            askQuestions();
            if(incorrectQuestions.size() == 0){
                System.out.println("You have answered all questions correctly. Study session complete!");
                break;
            }else{
                int numberOfCorrectQuestions = numberOfQuestions - incorrectQuestions.size();
                System.out.printf("You have answered %d out of %d questions correctly. (Studying progress: %.1f%%)\nReviewing incorrect questions...\n",
                        numberOfCorrectQuestions, numberOfQuestions, 100.0 * numberOfCorrectQuestions / numberOfQuestions);
                questionList.clear();
                questionList.addAll(incorrectQuestions);
                incorrectQuestions.clear();
                Main.printLine();
            }
        }
    }

    void askQuestions(){
        for (Question question : questionList) {
            question.askQuestion();
            question.checkAnswer(scanner.nextLine(), useLooseMarking);
            if(!question.isCorrect())
                incorrectQuestions.add(question);
            Main.printLine();
        }
    }

    boolean isQuestionValid(String[] questionArray){
        if(questionArray[0].isBlank()){
            System.out.println("WARNING: Detected empty question, skipping!");
            return false;
        }else if(questionArray.length == 1 || questionArray[1].isBlank()){
            System.out.println("WARNING: Detected question without an answer, skipping!");
            System.out.printf("Question skipped: \"%s\"\n", questionArray[0]);
            return false;
        }else if(questionArray.length > 2){
            for (int i = 2; i < questionArray.length; i++) {
                if(questionArray[i].isBlank()){
                    System.out.println("WARNING: Detected empty keyword, skipping!");
                    System.out.printf("Question skipped: \"%s\"\n", questionArray[0]);
                    return false;
                }
            }
        }
        return true;
    }
}

class Question{
    private final String questionString;
    private final String modelAnswer;
    private final List<String> keywordList;
    private boolean isCorrect;
    private final boolean allowLooseMarking;

    public Question(String[] strings){
        questionString = strings[0];
        modelAnswer = strings[1];

        keywordList = new ArrayList<>();
        if(strings.length > 2){
            allowLooseMarking = true;
            keywordList.addAll(Arrays.asList(strings).subList(2, strings.length));
        }else{
            allowLooseMarking = false;
        }
    }

    void askQuestion(){
        System.out.println(questionString);
    }

    void checkAnswer (String answer, boolean useLooseMarking){
        boolean isLooseMarking = useLooseMarking && allowLooseMarking;
        List<String> missedKeywords = new ArrayList<>();
        if(isLooseMarking){
            isCorrect = true;
            for (String keyword : keywordList) {
                if (!answer.toLowerCase().contains(keyword.toLowerCase())) {
                    isCorrect = false;
                    missedKeywords.add(keyword);
                }
            }
        }else{
            isCorrect = answer.toLowerCase().equals(modelAnswer.toLowerCase());
        }
        if(isCorrect){
            System.out.println("Correct!");
        }else{
            System.out.println("Incorrect.");
            if(isLooseMarking){
                System.out.printf("Your answer is missing %s: %s\n", missedKeywords.size() == 1 ? "this word" : "these words", missedKeywords.toString());
            }else{
                System.out.println("The correct answer is: " + modelAnswer);
            }
        }
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}