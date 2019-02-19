package Logic;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Car {
    private double Position;    // Car's position m

    // km/h -> m/s = km/h : 3,6
    private double Speed;       // Car's Speed    m/s
    private double MaxSpeed;   // Car's max speed ( can be road's max speed) m/s

    private double Acceleration; // Car's acceleration m/s

    private double lastDist = 0; // car's last distance to next car

    private Color color;

    private int width;
    private int height;

    private ArrayList<TrafficObject> objectInRange; // All objects in car's range
    private ArrayList<TrafficObject> allObject; // All objects

    private boolean shown;  //never used

    private boolean isSlowing = false; //never used

    private boolean showInDebug = true;

    private boolean showRange = true;

    private int road;

// CONSTRUCTORS -------------------------------------------------------------------

    public Car(double position, double speed, double maxspeed, double accele, int road) {
        this.Position = position;
        this.Speed = speed;
        this.MaxSpeed = maxspeed;
        this.Acceleration = accele;

        this.road = road;

        this.color = RandomColor();

        this.shown = true;

        this.width = 4;
        this.height = 2;

        this.objectInRange = new ArrayList<TrafficObject>();
    }

    public Car(double position, double speed, double maxspeed, double accele, int road, boolean debug) {
        this(position,speed,maxspeed,accele,road);
        this.showInDebug = debug;
    }

    public Car(double ms, double a) {
        this(0,0, ms, a,1);
    }

    public Car() {
        this(0,0,0,0, 1);
    }

    // METHODS ------------------------------------------------------------------------

    public void drive(double dt) {

        this.Speed += dt * Acceleration;
        this.Position += dt * Speed;

        // if no max Speed -> unlimited Acceleration
        if (this.MaxSpeed > 0) {
            this.Speed = Math.min(this.Speed, this.MaxSpeed);
        }
    }

    // if the car need to slow, the secur distance is already inferior to the disctance between the 2 cars
    public void slow(double dt, double dist, double a) {
        if (dt > 0) {
            if ( this.lastDist - dist < 10) {
                Double Deceleration = a * (dist - this.lastDist) / dt;

                System.out.println( " a = " + a + " , dist = " + new DecimalFormat("#.##").format(dist) + " , lastDist = " + new DecimalFormat("#.##").format(this.lastDist) + " , dt = " + new DecimalFormat("#.##").format(dt) + " , Dece = " + new DecimalFormat("#.##").format(Deceleration));

                if (this.Speed + dt * Deceleration <= 0) {
                    this.Speed = 0;
                } else {
                    this.Speed += dt * Deceleration;
                }

                this.Position += dt * Speed;
                this.lastDist = dist;
            }
        }
    }

    public double getSecurDistance() {
        return (((this.Speed*3.6)/10)*6 > 5) ? ((this.Speed*3.6)/10)*6 : 5;
    }

    public void changeRoad() {    // depasser
        if (this.road == 1) {
            this.road = 0;
        } else if (this.road == 0) {
            this.road = 1;
        }
    }

    public void goUp() {
        this.road = 0;
    }

    public void goDown() {
        this.road = 1;
    }

    public void autoDrive(double dt) {
        //System.out.println(this.allObject.get(1).getDistToObject());

        //Not unmovable obj
        if (this.getAcceleration() > 0) {
            if (this.objectInRange.size() == 0) {
                if (this.road == 0) {
                    this.goDown();
                }

                this.drive(dt);

                //check si obj in front and si il se rapproche et est dans la dist de secu
            } else if (this.isFrontObject() && this.isInRange(this.getFrontObject()) && this.lastDist > this.getFrontObject().getDistToObject() && this.getFrontObject().getRoad() == this.road) {
                // ===> can double
                //System.out.println((this.lastDist - this.getFrontObject().getDistToObject()) + " " + this.getSpeed()*3.6 );

                // obj in back and other road (en train de doubler) => slow
                if (this.isBackObject() && this.isInRange(this.getBackObject()) && this.getBackObject().getRoad() == 0 && this.road == 1) {
//                    System.out.println("slow while road = 1");
                    this.slow(dt, this.getFrontObject().getPosition() - this.getPosition() - this.width, 0.9d);


                    if (this.Speed <= 10d) {
                        System.out.println("11111 " + (this.getFrontSameRoadObject().getPosition() - this.getPosition() - this.width));
                    }



                // Voie 0 , car infront, car in range voie 1 => can't pass => slow :/
                } else if ( this.isBackObject() && this.isInRange(this.getBackObject()) && this.getBackObject().getRoad() == 1 && this.road == 0 ) {
//                    System.out.println("slow while road = 0");
                    this.slow(dt, this.getFrontObject().getPosition() - this.getPosition() - this.width, 0.9d);


                    if (this.Speed <= 10d) {
                        System.out.println("22222 " + (this.getFrontSameRoadObject().getPosition() - this.getPosition() - this.width));
                    }


                // 2 voie bloqued => STOP
                } else if (this.isRoadBloqued()) {
                    //System.out.println("slow while road bloqued");
                    this.slow(dt, this.getFrontSameRoadObject().getPosition() - this.getPosition() - this.width, 0.9d);


                    if (this.Speed <= 10d) {
                        System.out.println("3333 " + (this.getFrontSameRoadObject().getPosition() - this.getPosition() - this.width));
                    }


                } else {
                    //System.out.println("dunno");
                    this.changeRoad();
                    this.drive(dt);
                }

            } else if (this.isFrontObject() && this.isInRange(this.getFrontObject()) && this.lastDist > this.getFrontObject().getDistToObject() && this.getFrontObject().getRoad() != this.road){
                if (this.isRoadBloqued()) {
                    //System.out.println("slow while road bloqued");
                    this.slow(dt, this.getFrontObject().getPosition() - this.getPosition() - this.width, 0.9d);


                    if (this.Speed <= 10d) {
                        System.out.println("4444 " + (this.getFrontSameRoadObject().getPosition() - this.getPosition() - this.width) + " : " + this.lastDist);
                    }



                } else {
//                    System.out.println("well dunno again");
                    //this.changeRoad();
                    this.drive(dt);
                }
            } else {
                this.drive(dt);
            }

            if (this.isFrontObject()) {
                this.lastDist = this.getFrontObject().getDistToObject();
            }
        }
    }

    public boolean isRoadBloqued() {
        boolean a = false;
        boolean b = false;
        if (this.isFrontObject()) {
            for (TrafficObject to : objectInRange) {
                if (to.getDistToObject() > 0) {
                    if (to.getRoad() == 1) {
                        a = true;
                    } else if (to.getRoad() == 0) {
                        b = true;
                    }
                }
            }
        }
        return a && b;
    }

    public TrafficObject getFrontSameRoadObject() {
        if (this.isFrontObject()) {
            for (TrafficObject to : objectInRange) {
                if (to.getDistToObject() > 0) {
                    if (this.road == to.getRoad()) {
                        return to;
                    }
                }
            }
        }
        return null;
    }



    public boolean isFrontObject() {
        if (this.allObject.size() > 0) {
            for (TrafficObject to : this.allObject) {
                if (to.getDistToObject() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBackObject() {
        if (this.allObject.size() > 0) {
            for (TrafficObject to : this.allObject) {
                if (to.getDistToObject() < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public TrafficObject getBackObject() {
        // List Ranked by DistToObj
        for (int i = 0 ; i < this.allObject.size() ; i++) {
            if (this.allObject.get(i).getDistToObject() > 0) {
                return this.allObject.get(i - 2);
            }
        }
        return null;
    }

    public TrafficObject getFrontObject() {
        // List Ranked by DistToObj
        for (TrafficObject to : this.allObject) {
            if (to.getDistToObject() > 0) {
                return to;
            }
        }
        return null;
    }

    public boolean isInRange(TrafficObject c) {
        if (this.objectInRange.size() > 0) {
            for (TrafficObject to : this.objectInRange) {
                if (to.getPosition() == c.getPosition() && to.getRoad() == c.getRoad()) {
                    return true;
                }
            }
        }
        return false;
    }


    // Return a new random pastel color
    public Color RandomColor() {
        Random random = new Random();
        final float hue = random.nextFloat();
        // Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(4000) + 1000) / 10000f;
        final float luminance = 0.8f;
        final Color color = Color.getHSBColor(hue, saturation, luminance);

        return color;
    }

    // GETTERS & SETTERS ---------------------------------------------------------------

    public double getPosition() {
        return Position;
    }

    public void setPosition(double position) {
        Position = position;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public double getMaxSpeed() {
        return MaxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        MaxSpeed = maxSpeed;
    }

    public double getAcceleration() {
        return Acceleration;
    }

    public void setAcceleration(double acceleration) {
        Acceleration = acceleration;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public boolean isSlowing() {
        return isSlowing;
    }

    public void setSlowing(boolean slowing) {
        isSlowing = slowing;
    }

    public double getLastDist() {
        return lastDist;
    }

    public void setLastDist(double lastDist) {
        this.lastDist = lastDist;
    }

    public boolean isShowRange() {
        return showRange;
    }

    public void setShowRange(boolean showRange) {
        this.showRange = showRange;
    }

    public ArrayList<TrafficObject> getObjectInRange() {
        return objectInRange;
    }

    public void setObjectInRange(ArrayList<TrafficObject> objectInRange) {
        this.objectInRange = objectInRange;
    }

    public ArrayList<TrafficObject> getAllObject() {
        return allObject;
    }

    public void setAllObject(ArrayList<TrafficObject> allObject) {
        this.allObject = allObject;
    }

    public boolean isShowInDebug() {
        return showInDebug;
    }

    public void setShowInDebug(boolean showInDebug) {
        this.showInDebug = showInDebug;
    }
}
