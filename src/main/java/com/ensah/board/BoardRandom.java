package com.ensah.board;

import com.ensah.animals.Animal;
import com.ensah.animals.*;
import com.ensah.board.Player;
import com.ensah.board.Position;


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
import java.util.Random;


public class BoardRandom extends BoardGui{
    static Random random = new Random();


    public BoardRandom(Player player1, Player player2, String file){
        super(player1, player2, file);

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
                //boardStatus.setBoard(getBoardCurrent());
                //System.out.println(boardStatus.board);
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