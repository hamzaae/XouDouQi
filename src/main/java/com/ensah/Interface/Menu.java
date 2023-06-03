package com.ensah.Interface;


import com.ensah.board.BoardGUI;
import com.ensah.board.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.ensah.board.BoardGUI.actions;
import static com.ensah.board.BoardGUI.addAnimals;

public class Menu extends JPanel {
    private BufferedImage background;
    private final JButton singleButton,localButton,onlineButton;
    private final JButton soundButton,infoButton,exitButton;


    public Menu() {
        loadBackgroundImage();

        setLayout(null); // Use null layout for custom positioning of components

        // Create  buttons with images
        singleButton = createButton("/menu/single.png", 190, 310, 140, 50);
        localButton = createButton("/menu/local.png", 190, 370, 141, 53);
        onlineButton = createButton("/menu/online.png", 190, 430, 141, 53);
        soundButton = createButton("/sound.png",30, 370, 32, 32);
        infoButton =createButton("/info.png",30, 410, 32, 32);
        exitButton =createButton("/exit.png",30, 450, 32, 32);





        // Add buttons to the panel
        add(singleButton);
        add(localButton);
        add(onlineButton);
        add(soundButton);
        add(infoButton);
        add(exitButton);

        // Set the preferred size of the panel to match the background image size
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
    }

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(Menu.class.getResourceAsStream("/menu/jungle.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource(imagePath))));
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));

        button.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e){
                if (button == singleButton) {

                    JFrame frame = new JFrame("Single game ");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new com.ensah.Interface.Continue());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(singleButton);
                    currentFrame.dispose();



                }
                if (button == localButton) {
                    JFrame frame = new JFrame();
                    frame.setSize( 800, 612);
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JPanel pan = new JPanel();
                    pan.setBackground(Color.black);
                    pan.setBounds(64*7,0,800-64*7,612);
                    frame.add(pan);
                    BoardGUI boardGUI = new BoardGUI(new Player("1",false), new Player("2", true));
                    addAnimals();
                    //addBoardPieces();
                    frame.add(boardGUI);
                    actions(frame);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(localButton);
                    currentFrame.dispose();

                }
                if (button == onlineButton) {
                    JFrame frame = new JFrame("Single game ");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new Reseau());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(localButton);
                    currentFrame.dispose();
                }
                if (button == soundButton) {

                }
                if (button == infoButton) {
                    JFrame frame = new JFrame("Game rules");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new com.ensah.Interface.Rules());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
                if (button == exitButton) {
                    System.exit(0);

                }
            }


            @Override
            public void mouseEntered(MouseEvent e) {
                if (button == singleButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/single.png"))));


                }
                if (button == localButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/local.png"))));


                }
                if (button == onlineButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/online.png"))));

                }
                if (button == soundButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/sound.png"))));
                }
                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/info.png"))));
                }
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/exit.png"))));


                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button == singleButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/menu/single.png"))));
                }
                if (button == localButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/menu/local.png"))));
                }
                if (button == onlineButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/menu/online.png"))));
                }
                if (button == soundButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/sound.png"))));
                }
                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/info.png"))));
                }
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/exit.png"))));


                }
            }
        });



        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }



}
