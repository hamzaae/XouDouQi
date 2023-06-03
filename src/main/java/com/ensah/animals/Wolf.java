package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Wolf extends Animal {

    public Wolf(Position position, boolean isAlive, Player player){
        super("Wolf", 3, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\wolf.png",player);//power =4
    }

    public Wolf(Player player,Position position, boolean isAlive){
        super(player,"Wolf", 3, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\wolf.png");//power =4
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}
