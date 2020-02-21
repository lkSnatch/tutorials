package net.snatchTech.cacheSimple.dao;

import net.snatchTech.cacheSimple.model.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository("fakeCarDao")
public class FakeCarDao implements CarDao {

    // or use ConcurrentLinkedQueue for frequently written data instead
    private static List<Car> DB = new CopyOnWriteArrayList<>();

    @Override
    public UUID getCarOwnerByNumber(String number) {
        return getCarByNumber(number)
                .getOwner();
    }

    @Override
    public Car getCarByNumber(String number) {
        return DB.stream()
                .filter(car -> number.equals(car.getNumber()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find car by number " + number));
    }

    @Override
    public Car insertCar(UUID id, String number) {
        Car car = new Car(id, number);
        DB.add(car);
        return car;
    }

    @Override
    public Car insertCar(UUID id, String number, String make, String model) {
        Car car = new Car(id, number, make, model);
        DB.add(car);
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        return DB;
    }

    @Override
    public void setOwnerToCar(Car car, UUID owner) {
        car.setOwner(owner);
    }

}
