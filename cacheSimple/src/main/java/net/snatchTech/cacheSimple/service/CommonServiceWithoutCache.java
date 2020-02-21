package net.snatchTech.cacheSimple.service;

import net.snatchTech.cacheSimple.model.Car;
import net.snatchTech.cacheSimple.model.CarOwner;
import net.snatchTech.cacheSimple.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("CommonServiceWithoutCache")
@Service
public class CommonServiceWithoutCache implements CommonService {

    private final PersonService personService;
    private final CarService carService;

    @Autowired
    public CommonServiceWithoutCache(PersonService personService, CarService carService) {
        this.personService = personService;
        this.carService = carService;
    }

    public void setOwnerToCar(String number, String fullName) {

        Person person = personService.getByName(fullName);
        /*if (person == null) {
            throw new IllegalArgumentException("Person not found by name " + fullName);
        }*/

        Car car = carService.getByNumber(number);
        /*if (car == null) {
            throw new IllegalArgumentException("Car not found by number " + number);
        }*/

        carService.setOwnerToCar(car, person.getId());

    }

    public CarOwner getOwnerByNumber(String number) {
        Car car = carService.getByNumber(number);
        Person person = personService.getById(car.getOwner());
        return new CarOwner(car, person);
    }
}
