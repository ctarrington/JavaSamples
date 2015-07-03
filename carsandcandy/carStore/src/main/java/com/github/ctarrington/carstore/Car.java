package com.github.ctarrington.carstore;


import org.springframework.data.annotation.Id;

public class Car {
    @Id
    private String id;
    private String make;
    private String model;

    public Car(String id, String make, String model) {
        this.id = id;
        this.make = make;
        this.model = model;
    }

    public Car() {

    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }
}
