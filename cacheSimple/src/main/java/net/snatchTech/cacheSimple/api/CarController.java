package net.snatchTech.cacheSimple.api;


import net.snatchTech.cacheSimple.model.Car;
import net.snatchTech.cacheSimple.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/car")
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(path = "all")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(path = "get")
    public Car getByNumber(@RequestParam String number) {
        return carService.getByNumber(number);
    }

    @PostMapping(path = "add")
    public Car addCar(@RequestParam String number) {
        return carService.addCar(number);
    }

    @PostMapping(path = "addWithModel")
    public Car addCar(@RequestParam String number,
                       @RequestParam String make,
                       @RequestParam String model) {
        return carService.addCar(number, make, model);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
