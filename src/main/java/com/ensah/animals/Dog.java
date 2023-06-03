package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dog extends Animal {
    //right
    public Dog(Position position, boolean isAlive, Player player){
        super("Dog", 4, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\dog.png",player);
    }//power=3

    //left
    public Dog(Player player,Position position, boolean isAlive) {
        super(player, "Dog", 4, position, isAlive, "src\\main\\java\\com\\ensah\\media\\left\\dog.png");
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}

