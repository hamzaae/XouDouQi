
package com.ensah.network;

import com.ensah.Interface.Reseau;
import com.ensah.animals.Animal;
import com.ensah.board.Position;

import javax.swing.*;
import java.io.IOException;

import static com.ensah.board.BoardServer.animals;
import static com.ensah.board.BoardServer.player1;

public class ServerTh extends Thread{
    JFrame frame;
    public ServerTh(JFrame frame){
        this.frame = frame;
    }

    public void run(){
        while (true) {

            String rep;
            try {
                rep = Reseau.server.getMessageFromClient();

                while (rep == null) {
                    rep = Reseau.server.getMessageFromClient();
                }
                String[] arrOfStr = rep.split("\\s+");

                for (Animal a : animals) {
                    if (a.getName().equals(arrOfStr[0]) && a.getPlayer().getUsername().equals("2")) {
                        a.setPosition(new Position(Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[2])));
                    }
                    // Kill enemy // todoooo
                    if (a.getPosition().equals(new Position(Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[2]))) && a.getPlayer().getUsername().equals("1")) {
                        animals.remove(a);
                        System.out.println("Animal killed");
                        frame.repaint();
                        break;
                    }

                }
                player1.setTurn(true);
                frame.repaint();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

