package com.ensah.board;

import com.ensah.Interface.Display;
import com.ensah.animals.Animal;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class BoardLocal extends BoardGui{

    public BoardLocal(Player player1, Player player2, String file){
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


}