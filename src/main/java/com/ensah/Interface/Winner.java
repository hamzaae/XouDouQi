
package com.ensah.Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Winner extends JPanel {
    private BufferedImage background;

    private final JButton homeButton,againButton,exitButton;


    public Winner(String path) {
        loadBackgroundImage(path);
        setLayout(null); // Use null layout for custom positioning of components

        // Create  buttons with images

        homeButton = createButton("/home.png",185, 300, 32, 32);
        againButton =createButton("/again.png",230, 300, 32, 32);
        exitButton =createButton("/exit.png",275, 300, 32, 32);

        // Add buttons to the panel

        add(homeButton);
        add(againButton);
        add(exitButton);

        // Set the preferred size of the panel to match the background image size
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Xou DOU qi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Winner("/player1win.png"));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void loadBackgroundImage(String path) {
        try {
            background = ImageIO.read(Objects.requireNonNull(Menu.class.getResourceAsStream(path)));
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
                if (button == againButton) {
                    //String frameName = frame.getTitle();
                    //System.out.println("Closed Frame: " + frameName);
                    //if
                    System.out.println("again");

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
                if (button == againButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/again.png"))));
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

                if (button == againButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/again.png"))));
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
