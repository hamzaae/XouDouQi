package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Animal {

    public Elephant(Position position, boolean isAlive, Player player) {
        super("Elephant", 8, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\elephant.png",player);
    }
    public Elephant(Player player,Position position, boolean isAlive) {
        super(player,"Elephant", 8, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\elephant.png");
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}
