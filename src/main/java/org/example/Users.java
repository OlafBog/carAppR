package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Users {
    Vector<String> logins;
    Vector<String> passwords;
    Vector<Boolean> isAdmin;
    Vector<Integer> idOfRentedVehicle;

    Users(String path) {
        logins = new Vector<>();
        passwords = new Vector<>();
        isAdmin = new Vector<>();
        idOfRentedVehicle = new Vector<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parameters = line.split(";");
                logins.add(parameters[0]);
                passwords.add(parameters[1]);
                isAdmin.add(Boolean.parseBoolean(parameters[2]));
                idOfRentedVehicle.add(Integer.parseInt(parameters[3]));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public void addUser(String username, String password, boolean admin, int id) {
        logins.add(username);
        passwords.add(password);
        isAdmin.add(admin);
        idOfRentedVehicle.add(id);
    }

    public void save(String path) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for(String login : logins) {
            sb.append(login).append(";");
            sb.append(passwords.get(i)).append(";");
            sb.append(isAdmin.get(i)).append(";");
            sb.append(idOfRentedVehicle.get(i)).append(";");
            sb.append("\n");
            i++;
        }
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
