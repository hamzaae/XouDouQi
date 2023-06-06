/*package com.ensah.board;

public class BoardClient {
}

 */
package com.ensah.board;

import com.ensah.animals.Animal;
import com.ensah.animals.*;

import com.ensah.network.ClientProg;
import com.ensah.network.ClientTh;
//import com.ensah.minimax.Algorithm;

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
import java.util.Iterator;

public class BoardClient extends JPanel{
    public static ArrayList<Animal> animals = new ArrayList<>();
    public static Animal selectedAnimal;
    public static Player player1;
    public static Player player2;
    private static ArrayList<Position> possibleMoves = new ArrayList<>();

    public static ClientProg clientProg;
    public static Object rep;
    public static ClientTh responceTh;


    public BoardClient(Player player1, Player player2, String ipAdresse, JFrame frame){
        this.setPreferredSize(new Dimension(7*64,9*64));
        BoardClient.player1 = player1;
        BoardClient.player2 = player2;

        clientProg = ClientProg.getConnnection(ipAdresse);

        responceTh = new ClientTh(frame);
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
                            frame.repaint();
                            // End game
                            Player playerWin;
                            playerWin = endGame();
                            if (playerWin != null) {
                                // TODO
                                playerWin.setScore(playerWin.getScore() + 1);
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

                    selectedAnimal = null;
                    frame.repaint();
                }
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
        if (animal.getPlayer().getUsername().equals("2") && player2.isTurn()) {
            possibleMoves.addAll(checkMovements(animal));
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

    public static ArrayList<Position> checkMovements(Animal animal) {
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
    }



}

