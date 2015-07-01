package com.github.ctarrington.restboot;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class Candy {
    @Id
    private String id;
    private String name;
    private String size;

    public Candy(String id, String name, String size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Candy() {

    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getId() {
        return id;
    }
}
