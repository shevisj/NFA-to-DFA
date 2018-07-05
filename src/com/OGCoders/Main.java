package com.OGCoders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String fileName;
        String output;

        PrintWriter writer = null;

        if (args.length > 0) {
            fileName = args[0];
            output = args[1];
            try {
                writer = new PrintWriter(output, "UTF-8");
            } catch (IOException e) {
                System.out.println("Unable to create output file. Please try again.");
            }
        } else {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the nfa file to convert: ");

            fileName = input.nextLine();

            System.out.print("Enter the output file name: ");

            output = input.nextLine();

            try{
                writer = new PrintWriter(output, "UTF-8");
            } catch (IOException e) {
                System.out.println("Unable to create output file. Please try again.");
            }
        }

        try {

            FileHandler handler = new FileHandler(fileName);
            NFA nfa = handler.getContents();

            NFA_to_DFA converter = new NFA_to_DFA(nfa);

            writer.write(converter.convert());
            System.out.println(converter.convert());

            System.out.println("SUCCESS. ");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please start again.");
        } catch (Exception e2) {
            System.out.println("An unknown error occurred. Please try again.");
        }

        if (writer != null) {
            writer.close();
        }
    }
}
