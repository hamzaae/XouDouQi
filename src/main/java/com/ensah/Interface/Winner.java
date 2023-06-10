
package com.ensah.Interface;

import com.ensah.board.*;
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

public class Winner extends JPanel {
    private BufferedImage background;
    public static ServerApp server;

    private final JButton homeButton,againButton,exitButton;


    public Winner(String path) {
        loadBackgroundImage(path);
        setLayout(null);



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
                if (button == againButton) {
                    if (BoardGui.read("frame_names.txt").equals("XOU DOU QI LOCAL")) {
                        JFrame frame = new JFrame();
                        Display dis=new Display(frame);
                        BoardLocal boardLocal = new BoardLocal(new Player("1", false), new Player("2", true), "src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
                        boardLocal.addAnimals();
                        frame.add(boardLocal);
                        boardLocal.actions(frame);
                        frame.setVisible(true);
                    }
                    if (BoardGui.read("frame_names.txt").equals("XOU DOU QI EASY")) {
                        JFrame frame = new JFrame();
                        Display dis=new Display(frame);
                        BoardRandom boardRandom = new BoardRandom(new Player("1", false), new Player("2", true), "src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
                        boardRandom.addAnimals();
                        frame.add(boardRandom);
                        boardRandom.actions(frame);
                        frame.setVisible(true);
                        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(againButton);
                        currentFrame.dispose();
                    }

                     if (BoardGui.read("frame_names.txt").equals("XOU DOU QI S")) {
                        try {
                                server = new ServerApp();
                            JFrame frame = new JFrame();
                            Display dis=new Display(frame);
                            BoardServer boardServer = new BoardServer(new Player("1", true), new Player("2", false), frame, "src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
                            boardServer.addAnimals();
                            frame.add(boardServer);
                            boardServer.actions(frame);
                            frame.setVisible(true);
                            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(againButton);
                            currentFrame.dispose();

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                         }
                    if (BoardGui.read("frame_names.txt").equals("XOU DOU QI C")){
                        String ipAdresse=JOptionPane.showInputDialog("Please entrer the game's host IP  ");
                        JFrame frame = new JFrame();
                        Display dis=new Display(frame);

                        BoardClient boardClient = new BoardClient(new Player("1",false), new Player("2", false), ipAdresse, frame,"src\\main\\java\\com\\ensah\\utils\\newBoard.txt");
                        boardClient.addAnimals();
                        frame.add(boardClient);
                        boardClient.actions(frame);
                        frame.setVisible(true);
                        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(againButton);
                        currentFrame.dispose();
                    }



                }

                if (button == exitButton) System.exit(0);}





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
