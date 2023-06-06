package com.ensah.Interface;

import com.ensah.board.BoardRandom;
import com.ensah.board.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Modes extends JPanel {
    private BufferedImage background;
    private final JButton easyButton,mediumButton,hardButton;
    private final JButton homeButton,infoButton,exitButton;


    public Modes() {
        loadBackgroundImage();

        setLayout(null); // Use null layout for custom positioning of components

        // Create  buttons with images
        easyButton = createButton("/modes/easy.png", 190, 310, 140, 50);
        mediumButton = createButton("/modes/medium.png", 190, 370, 141, 53);
        hardButton = createButton("/modes/hard.png", 190, 430, 141, 53);
        homeButton = createButton("/home.png",30, 370, 32, 32);
        infoButton =createButton("/info.png",30, 410, 32, 32);
        exitButton =createButton("/exit.png",30, 450, 32, 32);

        // Add buttons to the panel
        add(easyButton);
        add(mediumButton);
        add(hardButton);
        add(homeButton);
        add(infoButton);
        add(exitButton);

        // Set the preferred size of the panel to match the background image size
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
    }

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(Menu.class.getResourceAsStream("/modes/levels.png")));
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
                if (button == easyButton) {
                    JFrame frame = new JFrame();
                    frame.setSize( 800, 612);
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JPanel pan = new JPanel();
                    pan.setBackground(Color.black);
                    pan.setBounds(64*7,0,800-64*7,612);
                    frame.add(pan);
                    BoardRandom boardRandom = new BoardRandom(new Player("1",false), new Player("2", true), "src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
                    boardRandom.addAnimals();
                    frame.add(boardRandom);
                    boardRandom.actions(frame);
                    frame.setVisible(true);


                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(easyButton);
                    currentFrame.dispose();

                }
                if (button == mediumButton) {
                    System.out.println("the second buttom");

                }
                if (button == hardButton) {
                    System.out.println("the third buttom");

                }
                if (button == homeButton) {

                    JFrame frame = new JFrame("Xou DOU qi");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new com.ensah.Interface.Menu());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
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
                if (button == easyButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/easy.png"))));
                }
                if (button == mediumButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/medium.png"))));
                }
                if (button == hardButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/hard.png"))));
                }


                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/exit.png"))));
                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/home.png"))));
                }
                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/info.png"))));
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button == easyButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/modes/easy.png"))));
                }
                if (button == mediumButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/modes/medium.png"))));
                }
                if (button == hardButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/modes/hard.png"))));
                }
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/exit.png"))));
                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/home.png"))));
                }

                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/info.png"))));
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
