package com.ensah;

import com.ensah.Interface.Display;
import com.ensah.Interface.Menu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {


        JFrame frame = new JFrame("Xou DOU Qi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }}

