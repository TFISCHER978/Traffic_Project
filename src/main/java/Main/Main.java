/*

package Main;

import GUI.GUI;
import Logic.Car;
import Logic.CarCollection;

import javax.swing.*;

public class Main {

    //TEMP -----------------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });

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


*/