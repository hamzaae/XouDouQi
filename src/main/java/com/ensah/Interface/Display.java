package com.ensah.Interface;

import com.ensah.board.BoardGUI;
import com.ensah.board.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.ensah.board.BoardGUI.actions;
import static com.ensah.board.BoardGUI.addAnimals;

public class Display {
    public static String str="";

    public static String hel;
    public static JTextPane console,text ;

    public Display(){
        JFrame frame = new JFrame();
        frame.setSize(850, 612);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel pan = new JPanel();
        pan.setBackground(Color.pink);
        pan.setLayout(null);
        pan.setBounds(64 * 7, 0, 850 - (64 * 7), 120);
        Font font=new Font("Cambria",Font.BOLD,14);

        /*text = new JTextPane();
        text.setBackground(Color.decode("#212121"));
        text.setBounds(0, 0, 850 - (64 * 7), 120);
        text.setForeground(Color.WHITE);
        text.setText("------------------ J U N G L E  G A M E ---------------- \n\n" + BoardGUI.word + "PLAYER 1 :\n \n" + "PLAYER 2 : \n\n");
        text.setFont(font);
        text.setEditable(false);
        pan.add(text);*/
        frame.add(pan);




        JPanel pan1 = new JPanel();
        pan1.setBackground(Color.decode("#212121"));
        pan1.setLayout(null);
        pan1.setBounds(64 * 7, 100, 850 - (64* 7), 480);
        frame.add(pan1);

        console= new JTextPane();
        console.setBackground(Color.decode("#212121"));
        console.setForeground(Color.white);
        console.setBounds(20, 0, 850 - (64 * 7), 480); // Set the size and position of the JTextPane
        hel = "\n";
        console.setText(hel);
        console.setEditable(false);
        console.setFont(font);
        pan1.add(console);
        JScrollPane sp = new JScrollPane(console);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(new EmptyBorder(0,0,0,0));
        sp.setBounds(0, 0, 850 - (64 * 7), 480); // Set the size and position of the JScrollPane
        pan1.add(sp);




        BoardGUI boardGUI = new BoardGUI(new Player("1", false), new Player("2", true));
        addAnimals();
        frame.add(boardGUI);
        actions(frame);
        frame.setTitle("Xou Dou Qi game");
        frame.setVisible(true);

    }
    /*public static void updateText() {
        String str = "------------------ J U N G L E  G A M E ---------------- \n\n";
        str += BoardGUI.word + "PLAYER 1 :\n \n";
        str += BoardGUI.word + "PLAYER 2 : \n\n";
        text.setText(str);
    }*/
}
