package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rat extends Animal {

    public Rat(Position position, boolean isAlive, Player player) {
        super("Rat", 1, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\rat.png",player);;//power =1
    }

    public Rat(Player player,Position position, boolean isAlive) {
        super(player,"Rat", 1, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\rat.png");;//power =1
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}
