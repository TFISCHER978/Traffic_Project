package Logic;


import java.util.ArrayList;

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


    public void drive(double dt) {
        for (Car c : CarList) {
            c.drive(dt);

            System.out.print("Car n°" + CarList.indexOf(c) + " | ");

            System.out.printf("Car's Position : %.2f", c.getPosition());
            System.out.print(" | ");

            System.out.printf("Car's Speed : %.2f", c.getSpeed()*3.6d);
            System.out.print(" km/h");

            if (c.getMaxSpeed() > 0) {
                System.out.printf(" | Car's MaxSpeed : %.2f", c.getMaxSpeed() * 3.6d);
                System.out.print(" km/h");
            }

            System.out.println("");
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
