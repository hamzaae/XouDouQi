package com.ensah.board;

import com.ensah.animals.Animal;

import java.util.ArrayList;

public class Board {
    public ArrayList<Animal> board;
    public int p1Left = 8;
    public int p2Left = 8;

    public Board(ArrayList<Animal> board, int p1Left, int p2Left) {
        this.board = board;
        this.p1Left = p1Left;
        this.p2Left = p2Left;
    }

    public Board(ArrayList<Animal> board) {
        this.board = board;
    }
    public Board() {
    }

    public double evaluate(){
        float score=0;
        for (Animal animal:board){
            if(animal.getPlayer().getUsername().equals("1")){
                score+=1;
                score+=(animal.getPower()*0.1);
            }else{
                score-=1;
                score-=(animal.getPower()*0.1);
            }
        }
        return score;
    }

    public ArrayList<Animal> moveAnimal(Animal animal, Position position){
        animal.setPosition(position);
        return getBoard();
    }

    public ArrayList<Position> possibleAnimalMoves(Animal animal){
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (animal.getPlayer().isTurn()) {
            possibleMoves.addAll(animal.checkMovements(animal));
        }
        return possibleMoves;
    }

    public ArrayList<Animal> getBoardPlayer(String player){
        Animal a;
        ArrayList<Animal> playerPieces = new ArrayList<>();
        for (int row=0;row<9;row++){
            for (int p=0;p<7;p++){
                a=null;
                for (Animal animal:board){
                    if (animal.getPlayer().getUsername().equals(player)&&animal.getPosition().equals(new Position(p,row))){
                        a = animal;
                        playerPieces.add(animal);
                        break;
                    }
                }
                if (a==null){
                    playerPieces.add(null);
                }
            }
        }
        return playerPieces;
    }

    public ArrayList<Animal> getBoard(){
        Animal a;
        ArrayList<Animal> playerPieces = new ArrayList<>();
        for (int row=0;row<9;row++){
            for (int p=0;p<7;p++){
                a=null;
                for (Animal animal:board){
                    if (animal.getPosition().equals(new Position(p,row))){
                        a = animal;
                        playerPieces.add(animal);
                        break;
                    }
                }
                if (a==null){
                    playerPieces.add(null);
                }
            }
        }
        return playerPieces;
    }

    public void setBoard(ArrayList<Animal> board) {
        this.board = board;
    }
}
