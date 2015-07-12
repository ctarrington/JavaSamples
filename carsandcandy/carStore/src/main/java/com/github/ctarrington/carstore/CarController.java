package com.github.ctarrington.carstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value="/cars")
public class CarController {

    @Autowired
    private CarRepository repository;

    @RequestMapping(method= RequestMethod.GET)
    public List<Car> getAllCars(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        List<Car> cars = repository.findAll();
        return cars;
    }

    @RequestMapping(method= RequestMethod.POST)
    public Car addCar(@RequestBody @Valid final Car car) {
        Car saved = repository.save(car);
        return car;
    }

    @RequestMapping(method= RequestMethod.DELETE)
    public Object deleteCar(@RequestParam("id") String id) {
        repository.delete(id);
        return new EmptyJson();
    }

    @RequestMapping(method= RequestMethod.GET, value="{id}")
    public Car getCarByID(@PathVariable String id) {
        Car car = repository.findOne(id);
        return car;
    }

    @RequestMapping(method= RequestMethod.POST, value="/login")
    public Permissions login(@RequestBody @Valid final User user, HttpServletResponse response) {
        return new Permissions("CarStore vouches for "+user.getName());
    }

}
