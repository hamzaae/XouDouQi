
package com.ensah.board;

import com.ensah.Interface.Reseau;
import com.ensah.animals.Animal;
import com.ensah.network.ServerTh;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class BoardServer extends BoardGui{
    public static ServerTh responceTh;



    public BoardServer(Player player1, Player player2, JFrame frame, String file){
        super(player1, player2, file);

        responceTh = new ServerTh(frame);
        responceTh.start();

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
                    player1.setTurn(false);
                    try {
                        String line = selectedAnimal.getName() + " " + selectedAnimal.getPosition().getI() + " " + selectedAnimal.getPosition().getJ();
                        Reseau.server.sendMessageToClient(line);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);}
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



    /*public static ArrayList<Position> checkMovements(Animal animal) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        // Pos I & J
        int I=animal.getPosition().getI();
        int J=animal.getPosition().getJ();

        Position currentPosition = new Position(animal.getPosition().getI(),animal.getPosition().getJ());
        Position pLeft = new Position(currentPosition.getI()-1,currentPosition.getJ());
        Position pRight = new Position(currentPosition.getI()+1,currentPosition.getJ());
        Position pUp = new Position(currentPosition.getI(),currentPosition.getJ()-1);
        Position pDown = new Position(currentPosition.getI(),currentPosition.getJ()+1);
        Position pUpJump = new Position(currentPosition.getI(),currentPosition.getJ()-4);
        Position pDownJump = new Position(currentPosition.getI(),currentPosition.getJ()+4);
        Position pLeftJump = new Position(currentPosition.getI()-3,currentPosition.getJ());
        Position pRightJump = new Position(currentPosition.getI()+3,currentPosition.getJ());

        // Check Borders
        if (I>0) {possibleMoves.add(pLeft);}
        if (I<6) {possibleMoves.add(pRight);}
        if (J>0) {possibleMoves.add(pUp);}
        if (J<8) {possibleMoves.add(pDown);}
        // Check if not Swim (not rat) ,otherway: check river's border
        if (!animal.getName().equals("Rat")) {
            if ((I == 1 || I == 2 || I == 4 || I == 5) && J == 6) {
                possibleMoves.remove(pUp);
            }
            if ((I == 1 || I == 2 || I == 4 || I == 5) && J == 2) {
                possibleMoves.remove(pDown);
            }
            if (J == 3 || J == 4 || J == 5) {
                possibleMoves.remove(pLeft);
                possibleMoves.remove(pRight);
            }
        }
        // Check if Jump (Lion || Tiger)
        if (animal.getName().equals("Lion") || animal.getName().equals("Tiger")){
            if ((I == 1 || I == 2 || I == 4 || I == 5) && J == 6){possibleMoves.add(pUpJump);}
            if ((I == 1 || I == 2 || I == 4 || I == 5) && J == 2){possibleMoves.add(pDownJump);}
            if ((J == 3 || J == 4 || J == 5) && (I==0 || I==3)){possibleMoves.add(pRightJump);}
            if ((J == 3 || J == 4 || J == 5) && (I==6 || I==3)){possibleMoves.add(pLeftJump);}
        }

        // Check if not same Player's animal or Sanctuary or can jump Rat
        for (Animal a : animals) {
            // Check Rat and Jumpers
            if ((animal.getName().equals("Tiger") || animal.getName().equals("Lion"))&&a.getName().equals("Rat")){
                // Jump vertically
                if (animal.getPosition().getI()==a.getPosition().getI() && animal.getPosition().getJ()==2) {
                    for (int i = 1; i < 4; i++) {
                        if (animal.getPosition().getJ() + i == a.getPosition().getJ()) {
                            possibleMoves.remove(pDownJump);
                            break;
                        }
                    }
                }
                if (animal.getPosition().getI()==a.getPosition().getI() && animal.getPosition().getJ()==6){
                    for (int i=3;i>0;i--){
                        if (animal.getPosition().getJ()-i==a.getPosition().getJ()){
                            possibleMoves.remove(pUpJump);
                            break;
                        }
                    }

                }
                // Jump horizontally
                if (animal.getPosition().getJ()==a.getPosition().getJ() && (animal.getPosition().getI()==0||animal.getPosition().getI()==3)) {
                    for (int i = 1; i < 3; i++) {
                        if (animal.getPosition().getI() + i == a.getPosition().getI()) {
                            possibleMoves.remove(pRightJump);
                            break;
                        }
                    }
                }
                if (animal.getPosition().getJ()==a.getPosition().getJ() && (animal.getPosition().getI()==6||animal.getPosition().getI()==3)) {
                    for (int i=2;i>0;i--){
                        if (animal.getPosition().getI()-i==a.getPosition().getI()){
                            possibleMoves.remove(pLeftJump);
                            break;
                        }
                    }

                }

            }
            Iterator<Position> positionIterator = possibleMoves.iterator();
            while (positionIterator.hasNext()) {
                Position p = positionIterator.next();
                // Not same player animal
                if (a.getPlayer().isTurn() && p.equals(a.getPosition())) {
                    positionIterator.remove();
                    break;
                }
                // Not 1st player Sanctuary
                if (animal.getPlayer().getUsername().equals("1") && p.equals(new Position(3, 0))) {
                    positionIterator.remove();
                    break;
                }
                // Not 2nd player Sanctuary
                if (animal.getPlayer().getUsername().equals("2") && p.equals(new Position(3, 8))) {
                    positionIterator.remove();
                    break;
                }
                // Check Rat and Elephant
                if (p.equals(a.getPosition()) && !animal.canKill(animal, a)) {
                    positionIterator.remove();
                    break;
                }

            }
        }


        return possibleMoves;
    }*/


}
