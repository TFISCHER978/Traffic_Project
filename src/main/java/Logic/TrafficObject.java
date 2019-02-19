package Logic;

public class TrafficObject {

    private double position;
    private int road;
    private double distToObject;
    private double objectSecurDist;

    public TrafficObject(double position, int road, double distToObject, double objectSecurDist) {
        this.position = position;
        this.road = road;
        this.distToObject = distToObject;
        this.objectSecurDist = objectSecurDist;
    }

    public TrafficObject(double position, int road, double distToObject) {
        this.position = position;
        this.road = road;
        this.distToObject = distToObject;
        this.objectSecurDist = 0;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public double getDistToObject() {
        return distToObject;
    }

    public void setDistToObject(double distToObject) {
        this.distToObject = distToObject;
    }

    public double getObjectSecurDist() {
        return objectSecurDist;
    }

    public void setObjectSecurDist(double objectSecurDist) {
        this.objectSecurDist = objectSecurDist;
    }
}
