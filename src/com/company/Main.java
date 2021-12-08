package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        while (true){
            String line = bufferedReader.readLine();
            if(line == null)
                break;
            System.out.println(line);
        }
    }
}
