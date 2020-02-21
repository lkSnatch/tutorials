package net.snatchTech.cacheSimple.service;

import net.snatchTech.cacheSimple.dao.CarDao;
import net.snatchTech.cacheSimple.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private CarDao carDao;

    @Autowired
    public CarService(@Qualifier("fakeCarDao") CarDao carDao) {
        this.carDao = carDao;
    }

    public Car addCar(String number) {
        return carDao.insertCar(number);
    }

    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    public Car addCar(String number, String make, String model) {
        return carDao.insertCar(number, make, model);
    }

    public Car getByNumber(String number) {
        try {
            // imagine that this operation too complicated and DB has a worst connection
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Somebody has interrupted this thread " + Thread.currentThread().getName());
        }
        return carDao.getCarByNumber(number);
    }

    public void setOwnerToCar(Car car, UUID owner) {
        try {
            // imagine that this operation too complicated and DB has a worst connection
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Somebody has interrupted this thread " + Thread.currentThread().getName());
        }
        carDao.setOwnerToCar(car, owner);
    }
}
