package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Should be the main entry of the program
 */
public class Traffic extends JFrame {

    public Traffic() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(1200, 400);

        setTitle("Donut");
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