package com.github.ctarrington.restboot;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandyRepository extends MongoRepository<Candy, String> {

    public Candy findByName(String name);
    public List<Candy> findBySize(String size);

}