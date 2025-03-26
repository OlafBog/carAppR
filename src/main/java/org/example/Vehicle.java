package org.example;

abstract class Vehicle {
    private final String brand,model;
    private final int year,price;
    private boolean rented;
    private final int id,type;

    int GetId(){
        return id;
    }

    public String GetBrand() {
        return brand;
    }

    public String GetModel() {
        return model;
    }

    public int GetYear() {
        return year;
    }

    public int GetPrice() {
        return price;
    }

    public int GetType() {
        return type;
    }

    boolean GetRented(){
        return rented;
    }

    void ChangeRented(){
        rented = !rented;
    }

    Vehicle(String brand, String model, int year, int price, boolean rented, int id, int type) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = rented;
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj){
        return (this.getClass() == obj.getClass()) && this.toString().equals(obj.toString());
    }

    @Override
    abstract public int hashCode();

    public String toCSV() {
        return type +
                ";" +
                brand +
                ";" +
                model +
                ";" +
                year +
                ";" +
                price +
                ";" +
                rented +
                ";" +
                id;
    }

    @Override
    public String toString() {
        return " id: "+id+", brand: "+brand+", model: "+model+", year: "+year+", price: "+price+", rented: "+rented;
    }
}
