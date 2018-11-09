/*

package Main;

import java.util.ArrayList;
import Logic.Car;
import Logic.CarCollection;

import javax.swing.*;

public class Main {

    //TEMP -----------------------------------

    public static void main(String[] args) {

        CarCollection CC = new CarCollection();

        CC.addCar(new Car(10,17,0,0));
        CC.addCar(new Car(15,2,0,0));
        CC.addCar(new Car(9,50,0,0));
        CC.addCar(new Car(12,10,0,0));
        CC.addCar(new Car(2,5,0,0));

        ArrayList<Car> RankedCars = CC.getPosRankedCar();
        ArrayList<Car> Cars = CC.getCarList();


        System.out.println("Non Ranked Cars : ");
        for (Car c : Cars) {
            System.out.println(c.getPosition() + " : " + c.getSpeed());
        }

        System.out.println("\nRanked Cars : ");
        for (Car c : RankedCars) {
            System.out.println(c.getPosition() + " : " + c.getSpeed());
        }

    }
}


*/