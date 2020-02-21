package net.snatchTech.cacheSimple.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Car {

    private final UUID id;
    private final String number;
    private String make;
    private String model;
    private UUID owner;

    public Car(@JsonProperty("id") UUID id,
               @JsonProperty("number") String number) {
        this.id = id;
        this.number = number;
    }

    /*public Car(@JsonProperty("id") UUID id,
               @JsonProperty("number") String number,
               @JsonProperty("make") String make,
               @JsonProperty("model") String model) {
        this.id = id;
        this.number = number;
        this.make = make;
        this.model = model;
    }*/

    public Car(UUID id,
               String number,
               String make,
               String model) {
        this.id = id;
        this.number = number;
        this.make = make;
        this.model = model;
    }

    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
