
package com.ensah.board;

import com.ensah.Interface.Reseau;
import com.ensah.Interface.Winner;
import com.ensah.animals.Animal;
import com.ensah.network.ServerTh;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class BoardServer extends BoardGui {
    public static ServerTh responceTh;


    public BoardServer(Player player1, Player player2, JFrame frame, String file) {
        super(player1, player2, file);
        responceTh = new ServerTh(frame);
        responceTh.start();

    }


    public void actions(JFrame frame) {
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedAnimal != null) {
                    for (Position p : getMovedAnimalPossibleMoves(selectedAnimal)) {
                        if (p.getJ() == e.getY() / 64 && p.getI() == e.getX() / 64) {
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
                selectedAnimal = getMovedAnimal(e.getX(), e.getY());
                frame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedAnimal = getMovedAnimal(e.getX(), e.getY());

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
                            player1.setTurn(false);
                            possibleMoves.clear();
                            player1.setTurn(false);
                            // Kill the Enemy's Animal Or End Game
                            for (Animal a : animals) {
                                // Kill enemy
                                if (selectedAnimal.getPosition().equals(a.getPosition()) && a.getPlayer().getUsername().equals("2")) {
                                    animals.remove(a);
                                    System.out.println("Animal killed");
                                    break;
                                }
                            }
                            break;

                        }
                    }
                } catch (NullPointerException ex) {
                    System.out.println("No animal selected");
                }


                if (animalMoved) {
                    player1.setTurn(false);
                    try {
                        String line = selectedAnimal.getName() + " " + selectedAnimal.getPosition().getI() + " " + selectedAnimal.getPosition().getJ();
                        Reseau.server.sendMessageToClient(line);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    // End game
                    playerWin = endGame();
                    if (playerWin != null) {
                        if (playerWin.equals(player1)) {
                            JFrame winFrame = new JFrame("Xou DOU qi");
                            winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            winFrame.getContentPane().add(new Winner("/Ulost.png"));
                            winFrame.pack();
                            winFrame.setLocationRelativeTo(null);
                            winFrame.setVisible(true);
                        } else if (playerWin.equals(player2)) {
                            JFrame winFrame = new JFrame("Xou DOU qi");
                            winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            winFrame.getContentPane().add(new Winner("/Uwin.png"));
                            winFrame.pack();
                            winFrame.setLocationRelativeTo(null);
                            winFrame.setVisible(true);
                        }
                        player1.setTurn(false);
                        player2.setTurn(false);
                        frame.dispose();
                        playerWin.setScore(playerWin.getScore() + 1);
                    }
                }
                selectedAnimal = null;
                frame.repaint();
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