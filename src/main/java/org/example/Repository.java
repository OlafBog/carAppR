package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository implements IVehicleRepository{

    List<Vehicle> vehicles;

    Repository(){
        vehicles = new ArrayList<Vehicle>();
    }

    public boolean IsEmpty(){
        return vehicles.isEmpty();
    }

    @Override
    public Vehicle rentVehicle(int id) {
        for(Vehicle vehicle : vehicles){
            if (vehicle.GetId() == id) {
                if (vehicle.GetRented()) {
                    System.out.println("vehicle of id: " + id + " is unavailable");
                    return null;
                }
                System.out.println("rented vehicle of id: " + id);
                vehicle.ChangeRented();
                return vehicle;
            }
        }
        System.out.println("no vehicle of id: " + id);
        return null;
    }

    @Override
    public void returnVehicle(int id) {
        for(Vehicle vehicle : vehicles){
            if (vehicle.GetId() == id) {
                if (!vehicle.GetRented()) {
                    System.out.println("vehicle of id: " + id + " is already in stock");
                    return;
                }
                System.out.println("returned vehicle of id: " + id);
                vehicle.ChangeRented();
                return;
            }
        }
        System.out.println("no vehicle of id: " + id);
    }

    @Override
    public void getVehicles() {
        for(Vehicle vehicle : vehicles){
            System.out.println(vehicle.toString());
        }
    }

    public void getNotRentedVehicles() {
        for(Vehicle vehicle : vehicles){
            if (vehicle.GetRented()) {
                System.out.println(vehicle.toString());
            }
        }
    }

    @Override
    public void save(String path) {
        StringBuilder sb = new StringBuilder();
        for(Vehicle vehicle : vehicles){
            sb.append(vehicle.toCSV());
            sb.append("\n");
        }
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean compare(int id1, int id2){
        Vehicle v1 = null, v2 = null;
        for(Vehicle vehicle : vehicles){
            if (vehicle.GetId() == id1) v1 = vehicle;
            if (vehicle.GetId() == id2) v2 = vehicle;
        }
        if (v1 == null || !v1.equals(v2)){
            return false;
        } else
            return true;
    }

    int GetHashCode(int id) {
        for(Vehicle vehicle : vehicles){
            if(vehicle.GetId() == id){
                return vehicle.hashCode();
            }
        }
        return -1;
    }

    public void load(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            vehicles.clear();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parameters = line.split(";");
                switch (parameters[0]) {
                    case "1":
                        Car car = new Car(
                                parameters[1],parameters[2],Integer.parseInt(parameters[3]),
                                Integer.parseInt(parameters[4]),parameters[5].equals("true"),
                                Integer.parseInt(parameters[6]),Integer.parseInt(parameters[0])
                        );

                        vehicles.add(car);
                        break;
                    case "2":
                        Motorcycle motorcycle = new Motorcycle(
                                parameters[1],parameters[2],Integer.parseInt(parameters[3]),
                                Integer.parseInt(parameters[4]),parameters[5].equals("true"),
                                Integer.parseInt(parameters[6]),Integer.parseInt(parameters[0]),parameters[7]
                        );

                        vehicles.add(motorcycle);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
