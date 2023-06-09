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

public class Rules extends JPanel {
    private BufferedImage background;
    private JButton button;


    public Rules() {
        loadBackgroundImage();

        setLayout(null); // Use null layout for custom positioning of components


        // Set the preferred size of the panel to match the background image size
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        button = new JButton();
        button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/rules/ok.png"))));
        button.setBounds(240, 500, 32, 31);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(button);
        button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e){

                button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/shadow/ok.png"))));

            }
            public void mouseExited(MouseEvent e){

                button.setIcon(new ImageIcon(Objects.requireNonNull(Menu.class.getResource("/rules/ok.png"))));


            }
            public void mouseClicked(MouseEvent e){

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(button);
                currentFrame.dispose();


            }
        });
    }


    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(Menu.class.getResourceAsStream("/rules/rules.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}

