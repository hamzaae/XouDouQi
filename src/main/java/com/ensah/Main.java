package com.ensah;

import com.ensah.Interface.Display;
import com.ensah.Interface.Menu;
import com.ensah.board.BoardGUI;
import com.ensah.board.Player;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.ensah.board.BoardGUI.actions;
import static com.ensah.board.BoardGUI.addAnimals;


public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //new Display();

        JFrame frame = new JFrame("Xou DOU qi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }}

