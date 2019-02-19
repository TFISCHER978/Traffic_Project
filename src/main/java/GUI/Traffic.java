package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Should be the main entry of the program
 */
public class Traffic extends JFrame {

    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 50;
    private static final int HEIGHT = 600;


    public Traffic() {

        initUI();
    }

    private void initUI() {

        add(new Board(WIDTH,HEIGHT));

        setSize(WIDTH, HEIGHT);

        pack();

        setTitle("Traffic | Scale : + Up ; - Down | SimSpeed : - Left ; + Right");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Traffic ex = new Traffic();
            ex.setVisible(true);
        });
    }
}