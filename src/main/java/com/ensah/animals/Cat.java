package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Cat extends Animal {
    //right
    public Cat(Position position, boolean isAlive,Player player){
        super("Cat", 2, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\cat.png",player);
    }

    //left
    public Cat(Player player,Position position, boolean isAlive){
        super(player,"Cat", 2, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\cat.png");
        possiblePositions = new ArrayList<>();
        possiblePositions.add(new Position(3, 3));
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }

}

