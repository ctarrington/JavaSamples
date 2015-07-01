package com.github.ctarrington.restboot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/candy")
public class CandyController {

    @Autowired
    private CandyRepository repository;

    private static final List<Candy> candies = new ArrayList<>();

    @RequestMapping(method= RequestMethod.GET)
    public List<Candy> getAllCandy() {
        return repository.findAll();
    }

    @RequestMapping(method= RequestMethod.POST)
    public Candy addCandy(@RequestBody @Valid final Candy candy) {
        Candy saved = repository.save(candy);
        return saved;
    }
}
