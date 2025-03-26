package org.example;
import java.util.Objects;

public class Motorcycle extends Vehicle {
    String category;

    Motorcycle(String brand, String model, int year, int price, boolean rented, int id, int type, String category) {
        super(brand, model, year, price, rented, id, type);
        this.category = category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.GetBrand(), this.GetModel(), this.GetYear(),
                this.GetPrice(), this.GetRented(), this.GetId(),
                this.GetType(), this.category
        );
    }

    @Override
    public String toCSV() {
        return super.toCSV() + ";" + category;
    }

    @Override
    public String toString() {
        return "Motorcycle: "+super.toString()+", category: "+category;
    }
}
