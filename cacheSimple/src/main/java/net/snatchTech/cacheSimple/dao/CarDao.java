package net.snatchTech.cacheSimple.dao;

import net.snatchTech.cacheSimple.model.Car;

import java.util.List;
import java.util.UUID;

public interface CarDao {

    UUID getCarOwnerByNumber(String number);

    Car getCarByNumber(String number);

    Car insertCar(UUID id, String number);

    default Car insertCar(String number) {
        return insertCar(UUID.randomUUID(), number);
    }

    List<Car> getAllCars();

    void setOwnerToCar(Car car, UUID owner);

    Car insertCar(UUID id, String number, String make, String model);

    default Car insertCar(String number, String make, String model) {
        return insertCar(UUID.randomUUID(), number, make, model);
    }
}
