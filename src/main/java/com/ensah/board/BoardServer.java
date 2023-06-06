
package com.ensah.board;

import com.ensah.Interface.Reseau;
import com.ensah.animals.Animal;
import com.ensah.animals.*;

import com.ensah.network.ServerTh;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class BoardServer extends JPanel{

    public static LinkedList<Animal> animals = new LinkedList<>();
    public static Animal selectedAnimal;
    public static Player player1;
    public static Player player2;
    private static ArrayList<Position> possibleMoves = new ArrayList<>();
    public static ServerTh responceTh;

    //public static Object rep;


    public BoardServer(Player player1, Player player2, JFrame frame){
        this.setPreferredSize(new Dimension(7*64,9*64));
        BoardServer.player1 = player1;
        BoardServer.player2 = player2;

        //FirstMove firstMove = new FirstMove(frame);
        //firstMove.start();

        responceTh = new ServerTh(frame);
        responceTh.start();

    }


    public static void addAnimals(){
        // Player 1_left

        animals.add(new Rat(player1,new Position(0,2),true));
        animals.add(new Cat(player1,new Position(5,1),true));
        animals.add(new Dog(player1,new Position(1,1),true));
        animals.add(new Wolf(player1,new Position(4,2),true));
        animals.add(new Leopard(player1,new Position(2,2),true));
        animals.add(new Tiger(player1,new Position(6,0),true));
        animals.add(new Lion(player1,new Position(0,0),true));
        animals.add(new Elephant(player1,new Position(6,2),true));

        // Player2_right

        animals.add(new Rat(new Position(6,6),true,player2));
        animals.add(new Cat(new Position(1,7),true,player2));
        animals.add(new Dog(new Position(5,7),true,player2));
        animals.add(new Wolf(new Position(2,6),true,player2));
        animals.add(new Leopard(new Position(4,6),true,player2));
        animals.add(new Tiger(new Position(0,8),true,player2));
        animals.add(new Lion(new Position(6,8),true,player2));
        animals.add(new Elephant(new Position(0,6),true,player2));
    }

    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        // Drawing board squares
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 7; x++) {
                if((x==1||x==2||x==4||x==5)&&(y==3||y==4||y==5)){
                    //g2D.setColor(new Color(35, 147, 200));

                    Image river;
                    try {
                        river = ImageIO.read(new File("src\\main\\java\\com\\ensah\\media\\river1.png")).getScaledInstance(100, 110, BufferedImage.SCALE_SMOOTH);
                        g.drawImage(river, x*64, y*64, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    //g2D.fillRect(x * 64, y * 64, 64, 64);

                } else if (((x==2||x==4)&&(y==0||y==8))||((y==1||y==7)&&x==3)) {
                    g2D.setColor(new Color(182, 109, 109));
                    g2D.fillRect(x * 64, y * 64, 64, 64);
                } else if (x==3&&(y==0||y==8)) {
                    g2D.setColor(new Color(84, 84, 84, 100));
                    g2D.fillRect(x * 64, y * 64, 64, 64);
                } else{
                    g2D.setColor(new Color(255, 255, 255));
                    g2D.fillRect(x * 64, y * 64, 64, 64);
                }

            }
        }
        g2D.setColor(Color.BLACK);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 7; x++) {
                int xPos = x * 64;
                int yPos = y * 64;
                g2D.drawRect(xPos, yPos, 64, 64);
            }
        }

        // Highlighting possible moves
        if (selectedAnimal != null) {
            g2D.setColor(new Color(68, 180, 57, 140));
            for (Position p : getMovedAnimalPossibleMoves(selectedAnimal)) {
                int r = p.getI();
                int c = p.getJ();
                g2D.fillRect(r * 64, c * 64, 64, 64);
            }
        }



        // Drawing animals
        for (Animal animal : animals) {
            g2D.drawImage(animal.getImage(), animal.getPosition().getI()*64, animal.getPosition().getJ()*64, this);
        }


    }

    public static void actions(JFrame frame){
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
                            // End game
                            Player playerWin;
                            playerWin = endGame();
                            if (playerWin != null) {
                                // TODO
                                playerWin.setScore(playerWin.getScore()+1);
                            }
                            break;

                        }
                    }
                }catch (NullPointerException ex){
                    System.out.println("No animal selected");
                }


                if (animalMoved) {
                    try {
                        String line = selectedAnimal.getName() + " " + selectedAnimal.getPosition().getI() + " " + selectedAnimal.getPosition().getJ();
                        Reseau.server.sendMessageToClient(line);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);}
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

    public static Animal getMovedAnimal(int x, int y){
        Position pA = new Position(x/64, y/64);
        for (Animal animal:animals){
            if(animal.getPosition().equals(pA)){
                return animal;
            }
        }
        return null;
    }

    public static ArrayList<Position> getMovedAnimalPossibleMoves(Animal animal){
        possibleMoves.clear();
        if (animal.getPlayer().getUsername().equals("1") && player1.isTurn()) {
            possibleMoves.addAll(animal.checkMovements(animal));
        }
        return possibleMoves;
    }

    public static void changePlayer(JFrame frame) {
        if (player1.isTurn()) {
            player1.setTurn(false);
            player2.setTurn(true);
        } else {
            player2.setTurn(false);
            player1.setTurn(true);
            //randomPlayer(frame);
            //boardStatus.board = getBoardCurrent();
            //System.out.println(boardStatus.board);
            //aiMove(boardStatus, frame);
        }
    }

    public static Player endGame(){
        if(selectedAnimal.getPosition().equals(new Position(3,8)) && selectedAnimal.getPlayer().getUsername().equals("1")){
            System.out.println("CheckMate P1 wins");
            return player1;
        }
        if(selectedAnimal.getPosition().equals(new Position(3,0)) && selectedAnimal.getPlayer().getUsername().equals("2")){
            System.out.println("CheckMate P2 wins");
            return player2;
        }
        return null;
    }




}
