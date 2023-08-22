/*package com.ensah.board;

public class BoardClient {
}

 */
package com.ensah.board;

import com.ensah.Interface.Winner;
import com.ensah.animals.Animal;


import com.ensah.network.ClientProg;
import com.ensah.network.ClientTh;



import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class BoardClient extends BoardGui{
    public static ClientProg clientProg;
    public static ClientTh responceTh;


    public BoardClient(Player player1, Player player2, String ipAdresse, JFrame frame, String file){
        super(player1, player2, file);
        clientProg = ClientProg.getConnnection(ipAdresse);
        responceTh = new ClientTh(frame);
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
                            player2.setTurn(false);
                            // Kill the Enemy's Animal Or End Game
                            for (Animal a : animals) {
                                // Kill enemy
                                if (selectedAnimal.getPosition().equals(a.getPosition()) && a.getPlayer().getUsername().equals("1")) {
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
                    player2.setTurn(false);
                    String line = selectedAnimal.getName() +" "+ selectedAnimal.getPosition().getI() +" "+ selectedAnimal.getPosition().getJ();
                    clientProg.send(line);
                    // End game
                    playerWin = endGame();

                    if (playerWin != null) {
                        if (playerWin.equals(player1)){
                            JFrame winFrame = new JFrame("Xou DOU qi");
                            winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            winFrame.getContentPane().add(new Winner("/Ulost.png"));
                            winFrame.pack();
                            winFrame.setLocationRelativeTo(null);
                            winFrame.setVisible(true);
                            Winner.clip2.start();

                        }else if (playerWin.equals(player2)) {
                            JFrame winFrame = new JFrame("Xou DOU qi");
                            winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            winFrame.getContentPane().add(new Winner("/Uwin.png"));

                            winFrame.pack();
                            winFrame.setLocationRelativeTo(null);
                            winFrame.setVisible(true);
                            Winner.clip1.start();
                        }
                        player1.setTurn(false);
                        player2.setTurn(false);
                        com.ensah.Interface.Menu.clip.stop();
                        frame.dispose();
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
            if ((animal.getName().equals("Tiger") || animal.getName().equals("Lion")) && a.getName().equals("Rat")){
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
