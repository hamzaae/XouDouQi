package com.ensah.network;

import com.ensah.animals.Animal;
import com.ensah.board.Position;

import javax.swing.*;

import static com.ensah.board.BoardClient.animals;
import static com.ensah.board.BoardClient.clientProg;

public class ClientTh extends Thread{
    JFrame frame;
    public ClientTh(JFrame frame){
        this.frame = frame;
    }

    public void run(){
        while (true){
            Object rep ;
            rep = clientProg.recieve();
            while (rep==null){
                rep = clientProg.recieve();
            }
            String[] arrOfStr = String.valueOf(rep).split("\\s+");
            for(Animal a:animals){
                if (a.getName().equals(arrOfStr[0]) && a.getPlayer().getUsername().equals("1")){
                    a.setPosition(new Position(Integer.parseInt(arrOfStr[1]),Integer.parseInt(arrOfStr[2])));
                    //break;
                }
                if (a.getPosition().equals(new Position(Integer.parseInt(arrOfStr[1]),Integer.parseInt(arrOfStr[2]))) && a.getPlayer().getUsername().equals("2")) {
                    animals.remove(a);
                    System.out.println("Animal killed");
                    break;
                }
            }
            frame.repaint();
        }
    }



}
