package org.example;

public interface IVehicleRepository {
    public Vehicle rentVehicle(int id);
    public void returnVehicle(int id);
    public void getVehicles();
    public void save(String path);
}
