package Logic;


import java.util.ArrayList;
import java.util.Comparator;

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


    // GETTERS & SETTERS --------------------------------------------------------------

    public ArrayList<Car> getCarList() {
        return CarList;
    }

    public void setCarList(ArrayList<Car> carList) {
        CarList = carList;
    }
}
