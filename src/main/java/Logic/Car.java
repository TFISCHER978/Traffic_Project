package Logic;

public class Car {
    private double Position;    // Car's position m

    // km/h -> m/s = km/h : 3,6
    private double Speed;       // Car's Speed    m/s
    private double MaxSpeed;   // Car's max speed ( can be road's max speed) m/s

    private double Acceleration; // Car's acceleration m/s

    // CONSTRUCTORS -------------------------------------------------------------------

    public Car(double p, double s, double ms, double a) {
        this.Position = p;
        this.Speed = s;
        this.MaxSpeed = ms;
        this.Acceleration = a;
    }

    public Car() {
        this(0,0,0,0);
    }

    // METHODS ------------------------------------------------------------------------

    public void drive(double dt) {
        this.Position += dt*Speed;
        this.Speed += dt*Acceleration;
        // if no max Speed -> infinite Acceleration "Deja vu !!"
        if (this.MaxSpeed > 0 ) {
            this.Speed = Math.min(this.Speed, this.MaxSpeed);
        }
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
}
