package com.ensah.utils;

import com.ensah.animals.Animal;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LoadSaveBoard {

    public static String[][] loadGame(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
        int rows = 9;
        int columns = 7;
        String [][] myArray = new String[rows][columns];
        while(sc.hasNextLine()) {
            for (int i=0; i<myArray.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++) {
                    myArray[i][j] = line[j];
                }
            }
        }
        //System.out.println(Arrays.deepToString(myArray));
        return myArray;
    }

    public static void saveGame(ArrayList<Animal> board, String filePath) throws FileNotFoundException {
        try {
            FileWriter writer = new FileWriter(filePath);

            int rowIndex = 0;
            for (Animal animal : board) {
                if (animal == null){
                    writer.write("0");
                }else {
                    if (animal.getPlayer().getUsername().equals("1")) {
                        if (animal.getName().equals("Rat")) {
                            writer.write("R");
                        }
                        if (animal.getName().equals("Cat")) {
                            writer.write("C");
                        }
                        if (animal.getName().equals("Wolf")) {
                            writer.write("W");
                        }
                        if (animal.getName().equals("Dog")) {
                            writer.write("D");
                        }
                        if (animal.getName().equals("Leopard")) {
                            writer.write("P");
                        }
                        if (animal.getName().equals("Tiger")) {
                            writer.write("T");
                        }
                        if (animal.getName().equals("Lion")) {
                            writer.write("L");
                        }
                        if (animal.getName().equals("Elephant")) {
                            writer.write("E");
                        }
                    }
                    if (animal.getPlayer().getUsername().equals("2")) {
                        if (animal.getName().equals("Rat")) {
                            writer.write("r");
                        }
                        if (animal.getName().equals("Cat")) {
                            writer.write("c");
                        }
                        if (animal.getName().equals("Wolf")) {
                            writer.write("w");
                        }
                        if (animal.getName().equals("Dog")) {
                            writer.write("d");
                        }
                        if (animal.getName().equals("Leopard")) {
                            writer.write("p");
                        }
                        if (animal.getName().equals("Tiger")) {
                            writer.write("t");
                        }
                        if (animal.getName().equals("Lion")) {
                            writer.write("l");
                        }
                        if (animal.getName().equals("Elephant")) {
                            writer.write("e");
                        }
                    }
                }
                writer.write(' '); // Add a space between characters
                rowIndex++;
                if (rowIndex == 7) {
                    writer.write(System.lineSeparator()); // Add a new line after each row
                    rowIndex = 0;
                }

            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /*public static void main(String args[]) throws Exception {
        loadGame("src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
    }*/
}
