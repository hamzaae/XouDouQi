package com.ensah.animals;


import com.ensah.board.BoardGui;
import com.ensah.board.Player;
import com.ensah.board.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Animal {
    protected String name;
    protected int power;
    protected Position position;
    protected boolean isAlive;
    protected Image image;
    protected Player player;
    protected ArrayList<Position> possiblePositions;



    //the_right constructor;
    public Animal(String name , int power, Position position, boolean isAlive, String image,Player player) {
        this.name=name;
        this.power=power;
        this.position=position;
        this.isAlive=isAlive;
        this.player=player;
        try {
            this.image = ImageIO.read(new File(image)).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Animal(Animal animal) {
        this.name= animal.getName();
        this.power= animal.getPower();
        this.position=new Position(animal.getPosition());
        this.isAlive=animal.isAlive;
        this.player=new Player(animal.getPlayer());
    }

    //the left constructor;
    public Animal(Player player,String name , int power, Position position, boolean isAlive, String image) {
        this.name=name;
        this.power=power;
        this.position=position;
        this.isAlive=isAlive;
        this.player=player;
        try {
            this.image = ImageIO.read(new File(image)).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Image getImage() {
        return image;
    }


    public String getName() {
        return name;
    }


    public int getPower() {
        return power;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public void move(Position position){

    };


    public ArrayList<Position> checkMovements(Animal animal) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        // Pos I & J
        int I=this.getPosition().getI();
        int J=this.getPosition().getJ();

        Position currentPosition = new Position(this.getPosition().getI(),this.getPosition().getJ());
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
            for (Animal a : BoardGui.animals) {
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
                    if (p.equals(a.getPosition()) && !canKill(animal, a)) {
                        positionIterator.remove();
                        break;
                    }

                }
            }


        return possibleMoves;
    }




    public boolean canKill(Animal animal, Animal enemy){
        if (animal.getName().equals("Rat") && enemy.getName().equals("Elephant") && ratInRiver(animal)) return false;
        if (animal.getName().equals("Elephant") && enemy.getName().equals("Rat")) return false;
        if (animal.getName().equals("Rat") && enemy.getName().equals("Elephant")) return true;
        if (animal.isTrapped(enemy)) return true;
        return animal.getPower() >= enemy.getPower();
    }

    public boolean ratInRiver(Animal animal){
        return animal.getPosition().equals(new Position(1,3))
                || animal.getPosition().equals(new Position(2,3))
                || animal.getPosition().equals(new Position(4,3))
                || animal.getPosition().equals(new Position(5,3))
                ||animal.getPosition().equals(new Position(1,4))
                || animal.getPosition().equals(new Position(2,4))
                || animal.getPosition().equals(new Position(4,4))
                || animal.getPosition().equals(new Position(5,4))
                || animal.getPosition().equals(new Position(1,5))
                || animal.getPosition().equals(new Position(2,5))
                || animal.getPosition().equals(new Position(4,5))
                || animal.getPosition().equals(new Position(5,5));
    }

    public boolean isTrapped(Animal enemy){
        // not simplified just to be easy to understand
        if ((enemy.getPosition().equals(new Position(2,8)) || enemy.getPosition().equals(new Position(4,8))
                || enemy.getPosition().equals(new Position(3,7))) && (getPlayer().getUsername().equals("2"))) return true;
        if ((enemy.getPosition().equals(new Position(2,0)) || enemy.getPosition().equals(new Position(4,0))
                || enemy.getPosition().equals(new Position(3,1))) && (getPlayer().getUsername().equals("1"))) return true;
        return false;
    }


}
