package GUI;

import Logic.Car;
import Logic.CarCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private CarCollection cc;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);

        cc = new CarCollection();

        cc.addCar(new Car(22,0.5d));
        cc.addCar(new Car(27,0.7d));
        cc.addCar(new Car(30,1d));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


        ArrayList<Car> carList = cc.getCarList();

        for (Car c : carList) {

            g2d.setColor(Color.gray);
            g2d.fillRect((int) c.getPosition(),200,40,20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateMissiles();

        repaint();
    }

    private void updateMissiles() {

        ArrayList<Car> carList = cc.getCarList();

        for (Car c : carList) {

            c.drive(0.5d);
        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
    }
}