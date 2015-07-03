package com.github.ctarrington.carstore;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> {

    public Car findByMake(String make);
    public List<Car> findByModel(String model);

}