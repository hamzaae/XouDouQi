package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lion extends Animal {

    public Lion(Position position, boolean isAlive, Player player) {
        super("Lion", 7, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\lion.png",player);//power=7
    }

    public Lion(Player player,Position position, boolean isAlive) {
        super(player,"Lion", 7, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\lion.png");//power=7
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}
