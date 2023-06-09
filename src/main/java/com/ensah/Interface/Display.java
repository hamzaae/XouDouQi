package com.ensah.Interface;

import com.ensah.utils.LoadSaveBoard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Display {
    private final JButton homeButton, saveButton, exitButton;

    public Display(JFrame frame) {
        frame.setSize(460, 700);
        frame.setResizable(false);
        JPanel pan = new JPanel();
        pan.setBounds(0, 575, 460, 200);
        pan.setBackground(Color.decode("#683612"));
        pan.setLayout(new FlowLayout());

        frame.setLocationRelativeTo(null);

        homeButton = createButton("/home.png", 0, 0, 32, 32);
        saveButton = createButton("/save.png", 0, 0, 32, 32);
        exitButton = createButton("/exit.png", 0, 0, 32, 32);
        pan.add(homeButton);
        pan.add(saveButton);
        pan.add(exitButton);
        frame.add(pan);
    }

    private JButton createButton(String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(Objects.requireNonNull(Display.class.getResource(imagePath))));
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));

        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (button == homeButton) {
                    JFrame frame = new JFrame("Xou DOU qi");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new com.ensah.Interface.Menu());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(homeButton);
                    currentFrame.dispose();
                }
                if (button == saveButton) {
                    try {
                        LoadSaveBoard.loadGame("src\\main\\java\\com\\ensah\\utils\\loadBoard.txt");
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (button == exitButton) {
                    System.exit(0);
                }
            }


            @Override
            public void mouseEntered(MouseEvent e) {
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/exit.png"))));
                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/home.png"))));
                }
                if (button == saveButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/save.png"))));
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/exit.png"))));
                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/home.png"))));
                }

                if (button == saveButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/save.png"))));
                }
            }
        });


        return button;
    }}
