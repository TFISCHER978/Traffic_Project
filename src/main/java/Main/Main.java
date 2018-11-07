package Main;

import Logic.Car;
import Logic.CarCollection;

public class Main {

    //TEMP -----------------------------------

    public static void main(String[] args) {

        CarCollection CC = new CarCollection();

        CC.addCar(new Car(22,0.5d));
        CC.addCar(new Car(27,0.7d));
        CC.addCar(new Car(30,1d));

        double FinalTime = 20;
        double Time = 0;
        double DeltaT = 1d;

        while (Time < FinalTime) {
            System.out.println("Time : " + (Time + 1) + " s -----------------");

            CC.drive(DeltaT);
            Time += DeltaT;

            System.out.println("");
        }
    }
}
