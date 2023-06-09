package com.ensah.board;

import com.ensah.animals.Animal;

import java.util.ArrayList;

public class Evaluation {
    int score;
    ArrayList<Animal> board;

    public Evaluation(int score, ArrayList<Animal> board) {
        this.score = score;
        this.board = board;
    }
}
