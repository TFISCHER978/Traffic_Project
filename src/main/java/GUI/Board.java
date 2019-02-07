package GUI;

import Logic.Car;
import Logic.CarCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    private static int SCALE = 2;  //

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
        setBackground(new Color(47,87,47));

        cc = new CarCollection();
        carList = cc.getCarList();
        rankedCarList = cc.getPosRankedCar();

        // Add some cars
        cc.addCar(new Car(100 * SCALE,0,ROADSPEED,0.5d,0));
        cc.addCar(new Car(500 * SCALE,0,ROADSPEED,0.7d,1));
        cc.addCar(new Car(50 * SCALE,0,ROADSPEED,0.8d,0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                if (SCALE < 5 ) SCALE ++;   //left
            }
            if(e.getButton() == MouseEvent.BUTTON2) { }
            if(e.getButton() == MouseEvent.BUTTON3) {
                if (SCALE > 1) SCALE --;    //right
            }
        }
        });


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

        fWidth = getParent().getWidth();
        fHeight = getParent().getHeight();

        carList = cc.getCarList();
        rankedCarList = cc.getPosRankedCar();
        
        g2d.setColor(Color.WHITE);

        g2d.drawString( "Road Size : " + fWidth / SCALE + " m", fWidth - 150, 220);
        g2d.drawString("Scale : " + SCALE, fWidth - 150, 240);
        for (int i = 0 ; i<rankedCarList.size() ;i++) {        	       
        	g2d.drawString("Voiture " + (i+1) +" : " + rankedCarList.get(i).getSpeed() * 3.6 +" km/h", 20 , 220 + 20*i);
        }
        int x1 = 0;    //abscice du point en haut à gauche du rectangle
        int y1 = 100;  //ordonnée du point en haut à gauche du rectangle
        int x2 = fWidth;
        int y2 = 20;

        for (Car c : carList) {

            if (c.getPosition() >= fWidth / SCALE) {
                c.setPosition(0);
            } else if (c.getPosition() < 0){
                c.setPosition(fWidth / SCALE);
            }

            if(c.isShown()) {

                int cW = c.getWidth()*SCALE;    // Longueur voiture
                int cH = c.getHeight()*SCALE;   // largeur voiture

                int cX = (int) c.getPosition()*SCALE;   // Position
                int cY = ((y2*SCALE)+y1*4)/4 + (c.getRoad() * 10 * SCALE);


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

            System.out.println("Car : " + i + " pos : " + c.getPosition());
            System.out.println("Car : " + i + " speed : " + c.getSpeed() + "\n");

            if (i == rankedCarList.size() - 1) {   // If it's the first car , continue to drive
                c.drive(SimulationSpeed);

            } else {        // Else maybe it need to slow
                if ( rankedCarList.get(i+1).getPosition() - c.getPosition() < 100d + c.getWidth()*SCALE) {   // MODIFY THIS SHIT -----------------------
                    c.setAcceleration(rankedCarList.get(i+1).getAcceleration());
                } else {
                    c.drive(SimulationSpeed);
                }
            }
        }
    }

    private void drawRoad(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        fWidth = getParent().getWidth();
        fHeight = getParent().getHeight();


        int x1 = 0;    //abscice du point en haut à gauche du rectangle
        int y1 = 100;  //ordonnée du point en haut à gauche du rectangle
        int x2 = fWidth;
        int y2 = 20;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(x1, y1, x2, y2 * SCALE);

        g2d.setColor(Color.YELLOW);

        Stroke dashed = new BasicStroke((int) (0.1 * SCALE), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(0, ((y2*SCALE)+y1*2)/2, fWidth,  ((y2*SCALE)+y1*2)/2);


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