package Logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Car {
    private double Position;    // Car's position m

    // km/h -> m/s = km/h : 3,6
    private double Speed;       // Car's Speed    m/s
    private double MaxSpeed;   // Car's max speed ( can be road's max speed) m/s

    private double Acceleration; // Car's acceleration m/s

    private Color color;

    private int width;
    private int height;

    private boolean shown;

    private int road;

// CONSTRUCTORS -------------------------------------------------------------------

    public Car(double p, double s, double ms, double a, int r) {
        this.Position = p;
        this.Speed = s;
        this.MaxSpeed = ms;
        this.Acceleration = a;

        this.road = r;

        this.color = RandomColor();

        this.shown = true;

        this.width = 4;
        this.height = 2;
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

    public double getSecurDistance() {
        return (this.Speed/10)*6;
    }

    public void slowWithDistance(double dt, double dist) {    // ralentir  ; simulationSpeed , distance to front car

    }

    public void changeRoad() {    // depasser
        if (this.road == 1) {
            this.road = 0;
        } else if (this.road == 0) {
            this.road = 1;
        }
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
}
