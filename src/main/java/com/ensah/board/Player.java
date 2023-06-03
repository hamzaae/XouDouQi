package com.ensah.board;

public class Player {
    private String username;
    private int score=0;

    private int animallife=8;

    private boolean turn;


    public Player(){

    }
    public Player(String username, boolean turn){
        this.username = username;
        this.turn = turn;
    }
    public Player (String username,int score,boolean turn,int animallife){
        this.username=username;
        this.score=score;
        this.turn=turn;
        this.animallife=animallife;
    }

    public int getAnimallife() {
        return animallife;
    }

    public void setAnimallife(int animallife) {
        this.animallife = animallife;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
