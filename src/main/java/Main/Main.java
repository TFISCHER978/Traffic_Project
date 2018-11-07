package Main;

import Logic.Car;

public class Main {

    //TEMP -----------------------------------

    public static void main(String[] args) {
        Car c = new Car(0,0,0,0.5d);

        double FinalTime = 21;
        double Time = 0;
        double DeltaT = 1;

        while (Time < FinalTime) {
            c.drive(DeltaT);
            Time += DeltaT;

            System.out.println("Time : " + Time + " s");
            System.out.println("Car's Position : " + c.getPosition());
            System.out.println("Car's Speed : " + c.getSpeed()*3.6d + " km/h");

            if (c.getMaxSpeed() > 0) {
                System.out.println("Car's MaxSpeed : " + c.getMaxSpeed() * 3.6d + " km/h");
            }

            System.out.println("");
        }
    }
}
