package GUI;

import Logic.Car;
import Logic.CarCollection;
import Logic.TrafficObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    private static int SCALE = 1;  //

    private static double SimulationSpeed = 0.2d; // Higher the number, faster the simulation

    private final static double ROADSPEED = 80; //Km/H

    private final int DELAY = 50;
    private Timer timer;
    private CarCollection cc;
    private ArrayList<Car> carList;
    private ArrayList<Car> rankedCarList;

    private boolean carRankinShowing = true;
    private boolean carsRangeShowing = true;
    private boolean carsLastDistShowing = true;
    private boolean carsSecurDistShowing = true;
    private boolean carsDistToFrontShowing = true;
    private boolean carsObjectInRangeShowing = true;

    private int fWidth;
    private int fHeight;

    private int delayBetweenAdd = 0;

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
        cc.addCar(new Car(350 * SCALE,(ROADSPEED-15)/3.6,(ROADSPEED-15)/3.6,2.8d,1));
        cc.addCar(new Car(150 * SCALE,ROADSPEED/3.6,ROADSPEED/3.6,3d,0));
        cc.addCar(new Car(250 * SCALE,ROADSPEED/3.6,ROADSPEED/3.6,3.1d,1));

//        cc.addCar(new Car(650 * SCALE,0,(ROADSPEED-10)/3.6,0.4d,1));
//        cc.addCar(new Car(600 * SCALE,0,(ROADSPEED+10)/3.6,3.5,0));
//        cc.addCar(new Car(800 * SCALE,0,ROADSPEED/3.6,3.2,0));

        //cc.addCar(new Car(100 * SCALE,0,90/3.6,3d,1));
        //cc.addCar(new Car(200 * SCALE,0,80/3.6,3d,1));

//        cc.addCar(new Car(700 * SCALE,0,80/3.6,0,1, false));
//        cc.addCar(new Car(700 * SCALE,0,80/3.6,0,0, false));
//        cc.addCar(new Car(1200 * SCALE,0,80/3.6,0,0));
//        cc.addCar(new Car(1300 * SCALE,0,80/3.6,0,0));
//        cc.addCar(new Car(1400 * SCALE,0,80/3.6,0,0));
//        cc.addCar(new Car(1500 * SCALE,0,80/3.6,0,0));
//        cc.addCar(new Car(1600 * SCALE,0,80/3.6,0,0));


        setFocusable(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {}
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    if (SCALE < 5 ) SCALE ++;
                }
                if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (SCALE > 1) SCALE --;
                }
                if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                     SimulationSpeed += 0.1d;
                }
                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                     SimulationSpeed -= 0.1d;
                }
                if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                    carsRangeShowing = !carsRangeShowing;
                    cc.showCarsRange(carsRangeShowing);
                    carsLastDistShowing = !carsLastDistShowing;
                    carsSecurDistShowing = !carsSecurDistShowing;
                    carsDistToFrontShowing = !carsDistToFrontShowing;
                    carsObjectInRangeShowing = !carsObjectInRangeShowing;
                    carRankinShowing = !carRankinShowing;
                }
                if (event.getKeyCode() == KeyEvent.VK_1 || event.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                    carRankinShowing = !carRankinShowing;
                }
                if (event.getKeyCode() == KeyEvent.VK_2 || event.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                    carsLastDistShowing = !carsLastDistShowing;
                }
                if (event.getKeyCode() == KeyEvent.VK_3 || event.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                    carsSecurDistShowing = !carsSecurDistShowing;
                }
                if (event.getKeyCode() == KeyEvent.VK_4 || event.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                    carsDistToFrontShowing = !carsDistToFrontShowing;
                }
                if (event.getKeyCode() == KeyEvent.VK_5 || event.getKeyCode() == KeyEvent.VK_NUMPAD5) {
                    carsObjectInRangeShowing = !carsObjectInRangeShowing;
                }
                if (event.getKeyCode() == KeyEvent.VK_6 || event.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                    carsRangeShowing = !carsRangeShowing;
                    cc.showCarsRange(carsRangeShowing);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//            if(e.getButton() == MouseEvent.BUTTON1) {
//                cc.addCar(new Car(50 * SCALE,(ROADSPEED-15)/3.6,ROADSPEED/3.6,2.8d,1));
//            }
//            if(e.getButton() == MouseEvent.BUTTON2) { }
//            if(e.getButton() == MouseEvent.BUTTON3) {
//                cc.addCar(new Car(50 * SCALE,(ROADSPEED-15)/3.6,ROADSPEED/3.6,2.8d,0));
//            }
//        }
//        });


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

        g2d.drawString( "Show info :   Space - All infos ;   1 - CarRanking ;   2 - SecurDistance ;   3 - DistanceToFront ;   4 - LastDistanceToFront ;   5 - ObjectInRange ;   6 - Range Circle", 10, 20);

        g2d.drawString( "Road Size : " + fWidth / SCALE + " m", fWidth - 160, 220);
        g2d.drawString( "Road Max Speed : " + ROADSPEED + " Km/h", fWidth - 160, 240);
        g2d.drawString("Scale : " + SCALE, fWidth - 160, 260);
        g2d.drawString("SimSpeed : " + new DecimalFormat("#.##").format(SimulationSpeed), fWidth - 160, 280);

        for (int i = 0 ; i<rankedCarList.size() ; i++) {
            if (rankedCarList.get(i).isShowInDebug()) {
                if (carRankinShowing) {
                    g2d.drawString("Car " + (i + 1) + " : " + new DecimalFormat("#.##").format(rankedCarList.get(i).getSpeed() * 3.6) + " km/h ; " + new DecimalFormat("#.##").format(rankedCarList.get(i).getPosition()) + " m", 20, 220 + 20 * i);
                }
                if ((i + 1) == rankedCarList.size()) {
                    if (carsSecurDistShowing) {
                        g2d.drawString("Secure Distance : " + new DecimalFormat("#.##").format(rankedCarList.get(i).getSecurDistance()) + "m", 300, 220 + 20 * i);
                    }
                } else {
                    if (carsSecurDistShowing) {
                        g2d.drawString("Secure Distance : " + new DecimalFormat("#.##").format(rankedCarList.get(i).getSecurDistance()) + "m", 300, 220 + 20 * i);
                    }
                    if (carsDistToFrontShowing) {
                        g2d.drawString("Distance to front car : " + new DecimalFormat("#.##").format(rankedCarList.get(i + 1).getPosition() - rankedCarList.get(i).getPosition() - rankedCarList.get(i).getWidth()), 450, 220 + 20 * i);

                    }
                }

                if (carsObjectInRangeShowing) {
                    ArrayList<TrafficObject> to = rankedCarList.get(i).getObjectInRange();
                    g2d.drawString("Object in range of Car " + (i + 1) + " : " + to.size() + " object => ", 20, 360 + 20 * i);


                    if (to.size() >= 1) {
                        for (int j = 0; j < to.size(); j++) {
                            g2d.drawString("pos " + (j + 1) + " : " + new DecimalFormat("#.##").format(to.get(j).getPosition()) + " ; distToObj : " + new DecimalFormat("#.##").format(to.get(j).getDistToObject()) + " ; ", 230 + 200 * j, 360 + 20 * i);
                        }
                    }

                }

                if (carsLastDistShowing) {
                    g2d.drawString("T = n-1 dist : " + new DecimalFormat("#.##").format(rankedCarList.get(i).getLastDist()), 180, 220 + 20 * i);
                }

                if (rankedCarList.get(i).isShowRange() && rankedCarList.get(i).getSpeed() > 5/3.6) {
                    g2d.setColor(Color.RED);
                    int range = (int) rankedCarList.get(i).getSecurDistance() * 2 * SCALE;
                    int x1 = (int) rankedCarList.get(i).getPosition() * SCALE - range / 2 + rankedCarList.get(i).getWidth() / 2;
                    int y1 = ((20 * SCALE) + 100 * 2) / 2 + (rankedCarList.get(i).getRoad() * 10 * SCALE) - range / 2 - rankedCarList.get(i).getHeight() / 2;

                    g2d.drawOval(x1, y1, range, range);

                    g2d.setColor(Color.WHITE);
                }
            }

        }

        int x1 = 0;    //abscice du point en haut à gauche du rectangle
        int y1 = 100;  //ordonnée du point en haut à gauche du rectangle
        int x2 = fWidth;
        int y2 = 20;

        for (Car c : carList) {

            if (c.getPosition() >= fWidth / SCALE) {
                cc.removeCar(c);
            } else if (c.getPosition() < 0){
                c.setPosition(fWidth / SCALE);
            }

            if(c.isShown()) {

                int cW = c.getWidth()*SCALE;    // Longueur voiture
                int cH = c.getHeight()*SCALE;   // largeur voiture

                int cX = (int) c.getPosition()*SCALE;   // Position
                int cY = ((y2*SCALE)+y1*4)/4 + (c.getRoad() * 10 *SCALE);


                g2d.setColor(c.getColor());
                g2d.fillRect(cX, cY, cW, cH);
            }
        }

    }

    public void actionPerformed(ActionEvent e) {

        updateCars();

        delayBetweenAdd++;

        if (delayBetweenAdd == 15) {
            cc.addRandomCar();
            //System.out.println("Add new Random Car");
            delayBetweenAdd = 0;
        }


        repaint();
    }

    // Where we drive cars ==============================================================================

    private void updateCars() {
        if (SimulationSpeed != 0.0d) {
            if (true) {
                for (int i = 0; i < rankedCarList.size(); i++) {
                    Car c = rankedCarList.get(i);

//                    if (i < rankedCarList.size() - 1) {
//                        c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - c.getWidth()));
//                    } else {
//                        c.setLastDist(0);
//                    }

                    c.setObjectInRange(cc.getObjectInRange(c));
                    c.setAllObject(cc.getAllObject(c));
                    c.autoDrive(SimulationSpeed);

                    // Remove Car si En dehors du cadre
                    if (c.getPosition() >= fWidth / SCALE) {
                        cc.removeCar(c);
                    }


                }
            } else {

                for (int i = 0; i < rankedCarList.size(); i++) {
                    Car c = rankedCarList.get(i);

                    if (c.getRoad() == 1) {

                        // Okey you're the first car, then go
                        if (i == rankedCarList.size() - 1) {
                            c.drive(SimulationSpeed);

//                } else if (i-1 >= 0) {
//
//                    // Someone in front of you, but where ?
//                    if (Math.abs(rankedCarList.get(i+1).getPosition() - c.getPosition() - (c.getWidth() )) < c.getSecurDistance() + c.getWidth()) {
//
//                        // Same road as you
//                        if (rankedCarList.get(i+1).getRoad() == 1) {
//
//                        }
//
//                    }


                            // Uh ? someome blocking your path ? then just change of road
                        } else if (Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())) < c.getSecurDistance() + c.getWidth() && rankedCarList.get(i + 1).getRoad() == 1) {
                            //c.changeRoad();
                            c.slow(SimulationSpeed, rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth()), 0.5d);
                            //c.drive(SimulationSpeed);

                            // Well . . . RUN !
                        } else {
                            c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())));
                            c.drive(SimulationSpeed);

                        }

                    } else if (c.getRoad() == 0) {

                        // So you're the first car uh ?
                        if (i == rankedCarList.size() - 1) {

                            // Okey you need more space to change back, so just drive here no problem
                            if (Math.abs(c.getPosition() - rankedCarList.get(i - 1).getPosition() - (rankedCarList.get(i - 1).getWidth())) < rankedCarList.get(i - 1).getSecurDistance() + rankedCarList.get(i - 1).getWidth()) {
                                c.drive(SimulationSpeed);

                                // Hey look ! you can go back now
                            } else if (Math.abs(c.getPosition() - rankedCarList.get(i - 1).getPosition() - (rankedCarList.get(i - 1).getWidth())) > rankedCarList.get(i - 1).getSecurDistance() + rankedCarList.get(i - 1).getWidth()) {
                                c.changeRoad();
                                c.drive(SimulationSpeed);
                            }

                        } else {
                            if (i - 1 >= 0) {
                        /* voiture entre 2 ?
                               =
                            =    =

                            UTILISER LA TAILLE DE LA VOITURE DANS LES CALCUL BLAIREAU c.getWidth()
                            ATTENTION, WTF SI DEJA VOITURE AUTRE VOIE

                         */

                                // Okey man, much space frontward and backward so can go back now
                                if (Math.abs(c.getPosition() - rankedCarList.get(i - 1).getPosition() - (rankedCarList.get(i - 1).getWidth())) > rankedCarList.get(i - 1).getSecurDistance() + rankedCarList.get(i - 1).getWidth() && Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition()) > c.getSecurDistance() + c.getWidth()) {
                                    c.changeRoad();
                                    c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())));
                                    c.drive(SimulationSpeed);

                                    // Hmm, so there's space backward but not so much in front of you, no problem just continue in this road
                                } else if (Math.abs(c.getPosition() - rankedCarList.get(i - 1).getPosition() - (rankedCarList.get(i - 1).getWidth())) > rankedCarList.get(i - 1).getSecurDistance() + rankedCarList.get(i - 1).getWidth() && Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition()) < c.getSecurDistance() + c.getWidth()) {
                                    c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())));
                                    c.drive(SimulationSpeed);

                                    // Well, so there's space frontward but not so much in your back, no problem just continue in this road
                                } else if (Math.abs(c.getPosition() - rankedCarList.get(i - 1).getPosition() - (rankedCarList.get(i - 1).getWidth())) < rankedCarList.get(i - 1).getSecurDistance() + rankedCarList.get(i - 1).getWidth() && Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition()) > c.getSecurDistance() + c.getWidth()) {
                                    c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())));
                                    c.drive(SimulationSpeed);

                                } else {
                                    c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())));
                                    c.drive(SimulationSpeed);
                                }
                            } else {
                                c.setLastDist(Math.abs(rankedCarList.get(i + 1).getPosition() - c.getPosition() - (c.getWidth())));
                                c.drive(SimulationSpeed);
                            }
                        }
                    }
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