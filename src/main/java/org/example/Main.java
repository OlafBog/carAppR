package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Repository repo = new Repository();
        Scanner scanner = new Scanner(System.in);
        int id = -1;
        String log = "", pas = "";
        boolean loggedIn = false, admin = false;
        String pathToUsers = "files/users.csv";
        while (true) {
            if (!loggedIn) {
                System.out.println("1 - login");
                System.out.println("2 - register");
                System.out.println("0 - exit");
                switch (scanner.nextLine()) {
                    case "0":
                        return;
                    case "1":
                        System.out.print("login: ");
                        String login = scanner.nextLine();
                        String goodPassword = "";
                        boolean isAdmin = false, found = false;
                        try (Scanner Fscanner = new Scanner(new File(pathToUsers))) {
                            while (Fscanner.hasNextLine()) {
                                String line = Fscanner.nextLine();
                                String[] parameters = line.split(";");
                                if (parameters[0].equals(login)) {
                                    found = true;
                                    goodPassword = parameters[1];
                                    isAdmin = parameters[2].equals("true");
                                    if (parameters[3].equals("none")) {
                                        id = -1;
                                    } else {
                                        id = Integer.parseInt(parameters[3]);
                                    }
                                }
                            }
                        } catch (FileNotFoundException e) {
                            System.err.println("File not found: " + e.getMessage());
                        }
                        if (!found) {
                            System.out.println("no such user");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.print("password: ");
                        String password = scanner.nextLine();
                        if (!goodPassword.equals(password)) {
                            System.out.println("wrong password");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.print("logged in");
                        loggedIn = true;
                        admin = isAdmin;
                        log = login;
                        pas = password;
                        break;
                    case "2":
                        while (true) {
                            System.out.print("create login: ");
                            login = scanner.nextLine();
                            boolean found2 = false;
                            try (Scanner Fscanner = new Scanner(new File(pathToUsers))) {
                                while (Fscanner.hasNextLine() && !found2) {
                                    String line = Fscanner.nextLine();
                                    String[] parameters = line.split(";");
                                    found2 = parameters[0].equals(login);
                                }
                            } catch (FileNotFoundException e) {
                                System.err.println("File not found: " + e.getMessage());
                            }
                            if (found2) {
                                System.out.println("this login already exists");
                                continue;
                            }
                            System.out.print("create password: ");
                            password = scanner.nextLine();
                            String userF = "";
                            try (Scanner Fscanner = new Scanner(new File(pathToUsers))) {
                                while (Fscanner.hasNextLine() && !found2) {
                                    String line = Fscanner.nextLine();
                                    String[] parameters = line.split(";");
                                    found2 = parameters[1].equals(password);
                                    userF = parameters[0];
                                }
                            } catch (FileNotFoundException e) {
                                System.err.println("File not found: " + e.getMessage());
                            }
                            if (found2) {
                                System.out.println("this password is allready used by " + userF);
                                continue;
                            }
                            Users newU = new Users(pathToUsers);
                            newU.addUser(log,pas,false,-1);
                            newU.save(pathToUsers);
                            System.out.println("new user created");
                            log = login;
                            pas = password;
                            break;
                        }
                }
            }
            String pathToRepo = "files/repository.csv";
            repo.load(pathToRepo);
            System.out.println("options:");
            System.out.println("1 - rent vehicle");
            System.out.println("2 - return vehicle");
            System.out.println("3 - list vehicles");
            System.out.println("4 - info");
            if (admin) {
                System.out.println("5 - add vehicle");
                System.out.println("6 - delete vehicle");
                System.out.println("7 - full list vehicles");
            }
            System.out.println("0 - logout");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    loggedIn = false;
                    admin = false;
                    break;
                case 1:
                    System.out.print("id of vehicle: ");
                    int tempId = scanner.nextInt();
                    if (id == -1) {
                        System.out.println("you are renting another vehicle (id:" + id + ")");
                        continue;
                    }
                    if (repo.rentVehicle(tempId) != null) {
                        id = tempId;
                    }
                    break;
                case 2:
                    if (id != -1) {
                        repo.returnVehicle(id);
                        id = -1;
                    } else {
                        System.out.println("you are not renting any vehicle");
                        continue;
                    }
                    break;
                case 3:
                    repo.getNotRentedVehicles();
                    break;
                case 4:
                    System.out.println("login: " + log);
                    System.out.println("password: " + pas);
                    if (id == -1) {
                        System.out.println("no rented vehicle");
                    } else {
                        System.out.println("rented vehicle: ");
                        for(Vehicle vehicle : repo.vehicles) {
                            if(vehicle.GetId() == id) System.out.println(vehicle.toString());
                        }
                    }
            }
            scanner.nextLine();
            scanner.nextLine();

        }
    }
}