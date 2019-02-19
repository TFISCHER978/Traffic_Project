package Logic;


import GUI.Traffic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Car's collection
 * gain access to all car's in the current run
 * Gui would access it to draw cars
 */
public class CarCollection {

    private ArrayList<Car> CarList;


    // CONSTRUCTORS -------------------------------------------------------------------

    public CarCollection() {
        this.CarList = new ArrayList<Car>();
    }


    // METHODS ----------------------------------------------------------------------

    public void addCar(Car c) {
        this.CarList.add(c);
    }

    public void addRandomCar() {


        Random random = new Random();

        int randomRoad = random.nextInt(2);
        int randomMaxSpeed = random.nextInt(90 + 1 - 65) + 65;
        int speed = randomMaxSpeed/2;
        double randomAcceleration = 2.8d + (4.5d - 2.8d) * random.nextDouble();

        //System.out.println("road : " + randomRoad + " ; maxSpeed : " + randomMaxSpeed + " ; speed : " + speed + " ; accele : " + randomAcceleration);

        Car c = new Car(0,speed,randomMaxSpeed/3.6,randomAcceleration/3.6,randomRoad);
        c.setShowRange(false);
        this.CarList.add(c);
    }

    public void removeCar(Car c) {
        this.CarList.remove(c);
    }

    // will return an ArrayList of car rank by their position
    public ArrayList<Car> getPosRankedCar() {
        // Dupe CarList
        ArrayList<Car> list = new ArrayList<Car>(CarList);
        list.sort(Comparator.comparingDouble(Car::getPosition));
        return list;
    }
    // will return an ArrayList of car rank by their speed
    public ArrayList<Car> getSpeedRankedCar() {
        // Dupe CarList
        ArrayList<Car> list = new ArrayList<Car>(CarList);
        list.sort(Comparator.comparingDouble(Car::getSpeed));
        return list;
    }


    public void drive(double dt) {
        for (Car c : CarList) {

            c.drive(dt);

        }
    }

    public void showCarsRange(boolean b) {
        for (Car c : CarList) {
            c.setShowRange(b);
        }
    }


    public ArrayList<TrafficObject> getObjectInRange(Car c) {
        ArrayList<TrafficObject> to = new ArrayList<TrafficObject>();

        double cRange = ((c.getSecurDistance() > 10) ? c.getSecurDistance() : 10) + 5;

        for (Car car : CarList) {
            if (car.getPosition() >= c.getPosition() - cRange && car.getPosition() <= c.getPosition() + cRange + c.getWidth() && car.getPosition() != c.getPosition()) {
                to.add(new TrafficObject(car.getPosition(), car.getRoad(),car.getPosition() - c.getPosition() - c.getWidth(), car.getSecurDistance()));
            }

        }
        to.sort(Comparator.comparingDouble(TrafficObject::getDistToObject));
        return to;
    }

    public ArrayList<TrafficObject> getAllObject(Car c) {
        ArrayList<TrafficObject> to = new ArrayList<TrafficObject>();

        for (Car car : CarList) {
            if (c.equals(car)) {
                to.add(new TrafficObject(car.getPosition(), car.getRoad(), 0, car.getSecurDistance()));
            } else {
                to.add(new TrafficObject(car.getPosition(), car.getRoad(), car.getPosition() - c.getPosition() - c.getWidth(), car.getSecurDistance()));
            }
        }

        to.sort(Comparator.comparingDouble(TrafficObject::getDistToObject));
        return to;
    }


    // GETTERS & SETTERS --------------------------------------------------------------

    public ArrayList<Car> getCarList() {
        return CarList;
    }

    public void setCarList(ArrayList<Car> carList) {
        CarList = carList;
    }
}
