package net.snatchTech.cacheSimple.service;

import net.snatchTech.cacheSimple.dao.CacheDao;
import net.snatchTech.cacheSimple.model.Car;
import net.snatchTech.cacheSimple.model.CarOwner;
import net.snatchTech.cacheSimple.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("CacheWriteBackService")
@Service
public class CacheWriteBackService implements CommonService {

    private final PersonService personService;
    private final CarService carService;
    private final CacheDao cacheDao;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    public CacheWriteBackService(PersonService personService, CarService carService, CacheDao cacheDao) {
        this.personService = personService;
        this.carService = carService;
        this.cacheDao = cacheDao;
    }

    public void setOwnerToCar(String number, String fullName) {

        // add cache record
        cacheDao.putRetrievedFromDBValue(number, (keyNumber, valueExistingCarOwner) -> {

            Car car;
            if (valueExistingCarOwner != null) {
                car = valueExistingCarOwner.getCar();
            } else {
                car = carService.getByNumber(keyNumber);
            }
            Person person = personService.getByName(fullName);

            return new CarOwner(car, person);
        });

        // set owner
        executorService.submit(() -> {
            CarOwner carOwner = cacheDao.get(number);
            carService.setOwnerToCar(carOwner.getCar(), carOwner.getOwner().getId());
        });

    }

    public CarOwner getOwnerByNumber(String number) {

        // get car with its owner and put it into the cache
        // if it is absent there
        return cacheDao.getAndPutValueIfAbsent(number, carNumber -> {
            Car car = carService.getByNumber(carNumber);
            Person person = personService.getById(car.getOwner());
            return new CarOwner(car, person);
        });

    }
}
