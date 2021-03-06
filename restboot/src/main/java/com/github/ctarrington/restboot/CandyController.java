package com.github.ctarrington.restboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/candies")
public class CandyController {

    @Autowired
    private CandyRepository repository;

    private static final List<Candy> candies = new ArrayList<>();

    @RequestMapping(method= RequestMethod.GET)
    public Map<String, List<Candy> > getAllCandy() {

        List<Candy> candies = repository.findAll();
        Map<String, List<Candy> > candiesMap = new HashMap<>();
        candiesMap.put("candies", candies);

        return candiesMap;
    }

    @RequestMapping(method= RequestMethod.POST)
    public Map<String,Candy> addCandy(@RequestBody @Valid final Map<String,Candy> candyMap) {
        Candy candy = candyMap.get("candy");
        Candy saved = repository.save(candy);

        Map<String,Candy> responseMap = new HashMap<>();
        responseMap.put("candy", saved);
        return responseMap;
    }

    @RequestMapping(method= RequestMethod.DELETE, value="{id}")
    public Object deleteCandy(@PathVariable String id) {
        repository.delete(id);
        return new EmptyJson();
    }

    @RequestMapping(method= RequestMethod.GET, value="{id}")
    public Map<String,Candy> getCandyByID(@PathVariable String id) {
        Candy candy = repository.findOne(id);

        Map<String,Candy> responseMap = new HashMap<>();
        responseMap.put("candy", candy);
        return responseMap;
    }

}
