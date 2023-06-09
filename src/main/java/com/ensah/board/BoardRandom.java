package com.ensah.board;

import com.ensah.Interface.Winner;
import com.ensah.animals.Animal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Random;


public class BoardRandom extends BoardGui{
    static Random random = new Random();
    private JFrame winFrame;


    public BoardRandom(Player player1, Player player2, String file){
        super(player1, player2, file);

    }

    public void actions(JFrame frame){


        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selectedAnimal != null){
                    for (Position p : getMovedAnimalPossibleMoves(selectedAnimal)) {
                        if (p.getJ()==e.getY()/64 && p.getI()==e.getX()/64){
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
                    randomPlayer();
                    frame.repaint();
                    changePlayer();
                    // End game
                    playerWin = endGame();
                    if (playerWin != null) {
                        if (playerWin.equals(player1)){
                            winFrame = new JFrame("Xou DOU qi");
                            winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            winFrame.getContentPane().add(new Winner("/Ulost.png"));
                            winFrame.pack();
                            winFrame.setLocationRelativeTo(null);
                            winFrame.setVisible(true);

                        }
                        else if (playerWin.equals(player2)){
                            winFrame = new JFrame("Xou DOU qi");
                            winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            winFrame.getContentPane().add(new Winner("/Uwin.png"));
                            winFrame.pack();
                            winFrame.setLocationRelativeTo(null);
                            winFrame.setVisible(true);
                        }


                        player1.setTurn(false);
                        player2.setTurn(false);
                        frame.dispose();

                        playerWin.setScore(playerWin.getScore()+1);
                    }
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

    public void randomPlayer(){
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