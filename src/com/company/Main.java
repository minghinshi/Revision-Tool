package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        Scanner scanner = new Scanner(System.in);
        boolean useLooseMarking = true;
        while (true){
            String line = bufferedReader.readLine();
            boolean hasLooseMarking = false;
            if(line == null)
                break;

            String[] strings = line.split("\\|");
            if(strings.length < 2)
                throw new RuntimeException("Your input does not include a question or answer!");
            else if (strings.length > 2 && useLooseMarking)
                hasLooseMarking = true;
            String question = strings[0];
            String modelAnswer = strings[1];
            String[] keyWords;

            System.out.println(question);
            String answer = scanner.nextLine();
            boolean correct = true;
            if(hasLooseMarking){
                keyWords = new String[strings.length - 2];
                System.arraycopy(strings, 2, keyWords, 0, strings.length - 2);
                for (String keyWord : keyWords) {
                    if (!answer.contains(keyWord)) {
                        correct = false;
                        break;
                    }
                }
            } else
                correct = answer.equals(modelAnswer);

            System.out.printf("Your answer is %s\n", (correct ? "correct!" : "incorrect."));
        }
    }
}

class Question{

}
