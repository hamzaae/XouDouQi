
package com.ensah.Interface;

import com.ensah.board.BoardClient;
import com.ensah.board.BoardServer;
import com.ensah.board.Player;
import com.ensah.network.ServerApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;




public class Reseau extends JPanel{
    public static ServerApp server;
    private BufferedImage background;
    private  JButton createButton,JoinButton;
    private  JButton homeButton,infoButton,exitButton;


    public Reseau() {

        loadBackgroundImage();

        setLayout(null); // Use null layout for custom positioning of components

        // Create  buttons with images
        createButton = createButton("/reseau/create.png", 190, 330, 141, 53);
        JoinButton = createButton("/reseau/join.png", 190, 400, 141, 53);
        homeButton = createButton("/home.png",30, 370, 32, 32);
        infoButton =createButton("/info.png",30, 410, 32, 32);
        exitButton =createButton("/exit.png",30, 450, 32, 32);

        // Add buttons to the panel
        add(createButton);
        add(JoinButton);
        add(homeButton);
        add(infoButton);
        add(exitButton);

        // Set the preferred size of the panel to match the background image size
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
    }

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(Menu.class.getResourceAsStream("/reseau/play_online.png")));
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
                if (button == createButton) {
                    try {
                        server = new ServerApp();
                        JFrame frame = new JFrame();
                        frame.setSize( 800, 612);
                        frame.setResizable(false);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        JPanel pan = new JPanel();
                        pan.setBackground(Color.black);
                        pan.setBounds(64*7,0,800-64*7,612);
                        frame.add(pan);
                        BoardServer boardNetwork = new BoardServer(new Player("1",true), new Player("2", false), frame);
                        BoardServer.addAnimals();
                        frame.add(boardNetwork);
                        BoardServer.actions(frame);
                        frame.setVisible(true);

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }


                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(createButton);
                    currentFrame.dispose();
                }
                if (button == JoinButton) {
                    String ipAdresse=JOptionPane.showInputDialog("Please entrer the game's host IP  ");

                    JFrame frame = new JFrame();
                    frame.setSize( 800, 612);
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JPanel pan = new JPanel();
                    pan.setBackground(Color.black);
                    pan.setBounds(64*7,0,800-64*7,612);
                    frame.add(pan);
                    BoardClient boardClient = new BoardClient(new Player("1",false), new Player("2", false), ipAdresse, frame);
                    BoardClient.addAnimals();
                    frame.add(boardClient);
                    BoardClient.actions(frame);
                    frame.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(JoinButton);
                    currentFrame.dispose();





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

                if (button == createButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/create.png"))));


                }
                if (button == JoinButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/join.png"))));

                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/home.png"))));
                }
                if (button == infoButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/info.png"))));
                }
                if (button == exitButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/exit.png"))));


                }

            }


            public void mouseExited(MouseEvent e) {
                if (button == createButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/reseau/create.png"))));
                }
                if (button == JoinButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/reseau/join.png"))));
                }
                if (button == homeButton) {
                    button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/home.png"))));
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

