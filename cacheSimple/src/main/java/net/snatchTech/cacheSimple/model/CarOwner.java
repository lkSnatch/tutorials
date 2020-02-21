package net.snatchTech.cacheSimple.model;

public class CarOwner {

    private final Car car;
    private final Person owner;

    public CarOwner(Car car, Person owner) {
        this.car = car;
        this.owner = owner;
    }

    public Car getCar() {
        return car;
    }

    public Person getOwner() {
        return owner;
    }
}
