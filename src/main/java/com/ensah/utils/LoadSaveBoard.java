package com.ensah.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class LoadSaveBoard {

    public static String[][] LoadGame(String file) throws FileNotFoundException {
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

    public static void main(String args[]) throws Exception {
        LoadGame("C:\\Users\\Microsoft\\Desktop\\XouDouQi\\src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
    }
}
