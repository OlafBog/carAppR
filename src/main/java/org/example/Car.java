package org.example;
import java.util.Objects;

public class Car extends Vehicle {

    Car(String brand, String model, int year, int price, boolean rented, int id, int type) {
        super(brand, model, year, price, rented, id, type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.GetBrand(),this.GetModel(),this.GetYear(),
                this.GetPrice(),this.GetRented(),this.GetId(),this.GetType()
        );
    }

    @Override
    public String toCSV() {
        return super.toCSV();
    }

    @Override
    public String toString() {
        return "Car: "+super.toString();
    }
}
