package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tiger extends Animal {

    public Tiger(Position position, boolean isAlive, Player player) {
        super("Tiger", 6, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\tiger.png",player);//power =6
    }

    public Tiger(Player player,Position position, boolean isAlive) {
        super(player,"Tiger", 6, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\tiger.png");//power =6
    }

    public void checkJump() {
        if(getPosition().equals(new Position(1,6))||getPosition().equals(new Position(2,6))||
                getPosition().equals(new Position(4,6))||getPosition().equals(new Position(5,6))){

        }
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}
