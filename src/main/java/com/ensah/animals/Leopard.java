package com.ensah.animals;

import com.ensah.board.Player;
import com.ensah.board.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Leopard extends Animal {

    public Leopard(Position position, boolean isAlive, Player player)
    {
        super("Leopard", 5, position, isAlive,"src\\main\\java\\com\\ensah\\media\\right\\leopard.png",player);
    }

    public Leopard(Player player,Position position, boolean isAlive)
    {
        super(player,"Leopard", 5, position, isAlive,"src\\main\\java\\com\\ensah\\media\\left\\leopard.png");
    }

    @Override
    public void move(Position position) {
        this.setPosition(position);
    }


}
