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

    private final static int COEF = 2;  //

    private final static double SimulationSpeed = 0.5d; // Higher the number, faster the simulation

    private final static double ROADSPEED = 20;

    private final int DELAY = 50;
    private Timer timer;
    private CarCollection cc;
    private ArrayList<Car> carList;
    private ArrayList<Car> rankedCarList;



    private int fWidth;
    private int fHeight;

    public Board(int w ,int h) {

        fWidth = w;
        fHeight = h;

        initBoard();
    }

    private void initBoard() {

        setPreferredSize(new Dimension(fWidth,fHeight));
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);

        cc = new CarCollection();
        carList = cc.getCarList();
        rankedCarList = cc.getPosRankedCar();

        // Add some cars
        cc.addCar(new Car(200,0,ROADSPEED,0.5d));
        cc.addCar(new Car(300,0,ROADSPEED,0.7d));
        cc.addCar(new Car(0,0,ROADSPEED,0.8d));



        // Start timer
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

        drawRoad(g);

        carList = cc.getCarList();
        rankedCarList = cc.getPosRankedCar();

        for (Car c : carList) {

            if (c.getPosition() >= getParent().getWidth()*COEF) {
                c.setPosition(0);
            } else if (c.getPosition() < 0){
                c.setPosition(getParent().getWidth()*COEF);
            }

            if(c.isShown()) {

                int cW = c.getWidth()*COEF;
                int cH = c.getHeight()*COEF;

                int cX = (int) c.getPosition()/COEF;
                int cY = 175 - cH/2;

                g2d.setColor(c.getColor());
                g2d.fillRect(cX, cY, cW, cH);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateCars();

        repaint();
    }

    // Where we drive cars ==============================================================================

    private void updateCars() {

        for (int i = 0 ; i < rankedCarList.size() ; i++) {
            Car c = rankedCarList.get(i);

            if (i == rankedCarList.size() - 1) {   // If it's the first car , continue to drive
                c.drive(SimulationSpeed);
                //System.out.println("car : " + i + " pos : " + c.getPosition());
            } else {        // Else maybe it need to slow
                if ( rankedCarList.get(i+1).getPosition() - c.getPosition() < 100d + c.getWidth()*COEF) {   // MODIFY THIS SHIT -----------------------
                    c.setAcceleration(rankedCarList.get(i+1).getAcceleration());
                } else {
                    c.drive(SimulationSpeed);
                }
            }
        }
    }

    private void drawRoad(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(47,87,47));

        fWidth = getParent().getWidth();
        fHeight = getParent().getHeight();

        //Green TOP
        g2d.fillRect(0,0, fWidth, fHeight-(fHeight - 100) );

        //Green Down
        g2d.fillRect(0,fHeight - ( fHeight - 200), fWidth,(fHeight-100));

        g2d.setColor(Color.YELLOW);

        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(0,150, fWidth,  150);

        dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{20}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(0,200, fWidth,  200);

        dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{20}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(0,100, fWidth,  100);

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