package com.ensah.board;

import com.ensah.animals.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BoardAlgo extends BoardGui{
    public BoardAlgo(Player player1, Player player2, String file){
        super(player1, player2, file);
    }

    @Override
    public void actions(JFrame frame){
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selectedAnimal != null){
                    for (Position p : getMovedAnimalPossibleMoves(selectedAnimal)) {
                        if (p.getJ()==e.getY()/carree && p.getI()==e.getX()/carree){
                            //selectedAnimal.setPosition((e.getX()/64),(e.getY()/64));
                            frame.repaint();
                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        frame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                selectedAnimal = getMovedAnimal(e.getX(),e.getY());
                frame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectedAnimal = getMovedAnimal(e.getX(),e.getY());

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boolean animalMoved = false;
                Position movedPosition = new Position(e.getX() / 64, e.getY() / 64);

                try {
                    possibleMoves = getMovedAnimalPossibleMoves(selectedAnimal);

                    for (Position p : possibleMoves) {
                        if (p.equals(movedPosition)) {
                            selectedAnimal.move(movedPosition);
                            animalMoved = true;
                            System.out.println("Animal moved successfully.");
                            possibleMoves.clear();
                            // Kill the Enemy's Animal Or End Game
                            for (Animal a : animals) {
                                // Kill enemy
                                if (selectedAnimal.getPosition().equals(a.getPosition()) && !a.getPlayer().isTurn()) {
                                    animals.remove(a);
                                    System.out.println("Animal killed");
                                    break;
                                }
                            }
                            break;

                        }
                    }
                }catch (NullPointerException ex){
                    System.out.println("No animal selected");
                }


                if (animalMoved) {
                    changePlayer();
                    frame.repaint();
                    // End game
                    playerWin = endGame();
                    if (playerWin != null) {
                        // TODO
                        player1.setTurn(false);
                        player2.setTurn(false);

                        playerWin.setScore(playerWin.getScore()+1);
                    }
                    //
                    mediumMove(getBoardCurrent(),frame);
                    //System.out.println(evaluate(getBoardCurrent()));
                }
                selectedAnimal = null;
            }


            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public int evaluate(ArrayList<Animal> board) {
        int score = 0;
        for (Animal animal : board) {
            if (animal != null) {
                if (animal.getPlayer().getUsername().equals("1")) {
                    score += 1;
                    score += animal.getPower();
                }
                if (animal.getPlayer().getUsername().equals("2")) {
                    score -= 1;
                    score -= animal.getPower();
                }
            }
        }
        return score;
    }


    public Evaluation minimax(ArrayList<Animal> board, int depth, boolean maxPlayer){
        if (depth==0 || endGame()!=null){
            return new Evaluation(evaluate(board), board);
        }
        if (maxPlayer){
            int maxEval = -10000; // what can be worst!
            MoveAlgo bestMove = null;
            for(MoveAlgo move:getAllMoves(board, player1)){
                int evaluation = minimax(move.board,depth-1,false).score;
                maxEval = Math.max(maxEval,evaluation);
                if (maxEval==evaluation){
                    bestMove = move;
                }
            }
            return new Evaluation(maxEval, bestMove.board);
        } else {
            int minEval = 10000; // what can be worst!
            MoveAlgo bestMove = null;
            for(MoveAlgo move:getAllMoves(board, player2)){
                int evaluation = minimax(move.board,depth-1,true).score;
                minEval = Math.min(minEval,evaluation);
                if (minEval==evaluation){
                    bestMove = move;
                }
            }
            return new Evaluation(minEval, bestMove.board);
        }
    }

    public ArrayList<MoveAlgo> getAllMoves(ArrayList<Animal> board, Player player){
        ArrayList<MoveAlgo> moves = new ArrayList<>();
        for(Animal animal:getAnimalsPlayer(player)){
            ArrayList<Position> validMoves = new ArrayList<>(getMovedAnimalPossibleMoves(animal));
            for(Position position:validMoves){
                // start copy
                ArrayList<Animal> tempBoard = new ArrayList<>();
                for (Animal animalCopy : board) {
                    if (animalCopy != null) {
                        tempBoard.add(new Animal(animalCopy));
                    } else {
                        tempBoard.add(null);
                    }
                }// end of copy
                tempBoard = simulateMove(animal, position, board);
                moves.add(new MoveAlgo(tempBoard, animal));
            }
        }
        return moves;
    }

    public void aiMove(ArrayList<Animal> board, JFrame frame){
        Evaluation evaluation;
        evaluation = minimax(board,1,true);
        for (Animal animal:animals){
            for (Animal a:evaluation.board){
                if (a != null && animal.getPlayer().equals(a.getPlayer()) && animal.getName().equals(a.getName())){
                    animal.setPosition(a.getPosition());
                }
            }
        }
        frame.repaint();
        player2.setTurn(true);
        player1.setTurn(false);

    }

    public ArrayList<Animal> simulateMove(Animal animal, Position position, ArrayList<Animal> tempBoard) {

        // Create a new ArrayList to hold the updated board after the move
        ArrayList<Animal> updatedBoard = new ArrayList<>(tempBoard);

        // Find the index of the animal in the board by comparing positions
        int animalIndex = -1;
        Animal currentAnimal = null;
        for (int i = 0; i < updatedBoard.size(); i++) {
            currentAnimal = updatedBoard.get(i);
            if (currentAnimal != null && currentAnimal.getPosition().equals(animal.getPosition())) {
                animalIndex = i;
                break;
            }
        }

        if (animalIndex != -1) {

            // Get the current position of the animal
            //Position currentPosition = updatedBoard.get(animalIndex).getPosition();

            // Calculate the target index in the updatedBoard based on the new position
            int targetIndex = (position.getJ() * 7) + position.getI();

            // Check if there is an animal at the target position
            Animal targetAnimal = updatedBoard.get(targetIndex);
            if (targetAnimal != null) {
                // Remove the target animal from the updated board
                updatedBoard.remove(targetAnimal);
            }

            // Swap the animal's position with the target position
            updatedBoard.set(animalIndex, null);
            currentAnimal.setPosition(position);
            updatedBoard.set(targetIndex, currentAnimal);

            //System.out.println("Before: "+currentAnimal.getName()+currentAnimal.getPosition());

            // Update the animal's position
            //System.out.println("After: "+currentAnimal.getName()+currentAnimal.getPosition());

            while (updatedBoard.remove(null)) {
            }


        }

        // Return the updated board
        return updatedBoard;
    }


    public void mediumMove(ArrayList<Animal> board, JFrame frame) {
        int maxEval = -10000;
        int currEval = evaluate(board);
        ArrayList<Animal> bestMove = null;
        ArrayList<ArrayList<Animal>> allMoves = new ArrayList<>();

        for (Animal animal : getAnimalsPlayer(player1)) {
            for (Position position : getMovedAnimalPossibleMoves(animal)) {
                //System.out.println(animal.getName()+position);
                ArrayList<Animal> tempBoard = new ArrayList<>();

                // Copy the animals from the original board to the tempBoard
                for (Animal animalCopy : board) {
                    if (animalCopy != null) {
                        tempBoard.add(new Animal(animalCopy));
                    } else {
                        tempBoard.add(null);
                    }
                }

                // Simulate the move
                tempBoard = simulateMove(animal, position, tempBoard);
                allMoves.add(tempBoard);

                int evaluation = evaluate(tempBoard);
                if (evaluation > maxEval) {
                    maxEval = evaluation;
                    bestMove = tempBoard;
                }
            }
        }

        if (maxEval != currEval) {
            System.out.println("Board changes");
            while (bestMove.remove(null)) {
            }
            //System.out.println(bestMove);
            /*for (Animal animal:animals){
                //assert bestMove!=null;
                for (Animal newAnimal:bestMove){
                    if (newAnimal!=null && newAnimal.getPosition() != animal.getPosition()){
                        System.out.println("okkkkkkkkkk");
                        animal.setPosition(newAnimal.getPosition());
                        //System.out.println("BBBBBBBefore: " + animal.getName()+ animal.getPosition());
                        System.out.println("newAnimal: "+ newAnimal.getName());
                        System.out.println(newAnimal.getPosition().getI());
                        System.out.println(newAnimal.getPosition().getJ());
                        animal.getPosition().setY(newAnimal.getPosition().getJ());
                        animal.getPosition().setX(newAnimal.getPosition().getI());
                        System.out.println("animal: "+ animal.getName());
                        System.out.println(animal.getPosition().getI());
                        System.out.println(animal.getPosition().getJ());
                        //System.out.println("AAAAAAAfter: " +  animal.getName()+ animal.getPosition());

                    }
                }
            }*/
            for (Animal animal:animals){
                for (Animal newAnimal:bestMove){
                    if (animal.getName().equals(newAnimal.getName()) && animal.getPlayer().getUsername().equals(newAnimal.getPlayer().getUsername())){
                        if (animal.getPosition().getI()==newAnimal.getPosition().getI() && animal.getPosition().getJ()==newAnimal.getPosition().getJ()){
                            bestMove.set(bestMove.indexOf(newAnimal), animal);
                        }else{
                            animal.setPosition(newAnimal.getPosition());
                            bestMove.set(bestMove.indexOf(newAnimal), animal);
                        }
                        break;
                    }
                }
            }
            for (Animal animal:bestMove){
                System.out.println(animal.getName()+animal.getPosition()+animal.getPlayer().getUsername());
            }
            System.out.println(bestMove);
            animals=bestMove;


        }else {
            System.out.println("No changes");
            randomPlayer();
        }


        frame.repaint();
        player2.setTurn(true);
        player1.setTurn(false);
    }


    public static boolean areAnimalArraysEqual(ArrayList<Animal> animals1, ArrayList<Animal> animals2) {
        // Check if the arrays have the same size
        if (animals1.size() != animals2.size()) {
            return false;
        }

        // Compare the elements of the arrays
        for (int i = 0; i < animals1.size(); i++) {
            Animal animal1 = animals1.get(i);
            Animal animal2 = animals2.get(i);
            if (animal2!=null && animal1!=null){

                // If the animals are not equal or have different positions, the arrays are not the same
                if (!areAnimalsEqual(animal1, animal2) || !animal1.getPosition().equals(animal2.getPosition())) {
                    return false;
                }
            }
        }

        // All elements are equal, the arrays are the same
        return true;
    }

    private static boolean areAnimalsEqual(Animal animal1, Animal animal2) {
        // Compare the animals based on their properties
        return animal1.getClass().equals(animal2.getClass()) &&
                animal1.getPlayer().equals(animal2.getPlayer());
    }

    public void randomPlayer(){
        Random random = new Random();
        int randomAnimalIndex = random.nextInt(animals.size());
        while (!animals.get(randomAnimalIndex).getPlayer().getUsername().equals("1")){
            randomAnimalIndex = random.nextInt(animals.size());
        }
        Animal randomAnimal = animals.get(randomAnimalIndex);

        ArrayList<Position> possibleMoves = getMovedAnimalPossibleMoves(randomAnimal);
        if (!possibleMoves.isEmpty()) {
            int randomMoveIndex = random.nextInt(possibleMoves.size());
            randomAnimal.move(possibleMoves.get(randomMoveIndex));
        }
        // Kill enemy
        for (Animal a : animals) {
            if (randomAnimal.getPosition().equals(a.getPosition()) && !a.getPlayer().isTurn()) {
                animals.remove(a);
                break;
            }
        }
    }




}
