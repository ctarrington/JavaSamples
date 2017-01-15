package com.github.ctarrington.trystreams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    String raw = "The Quick brown fox jumped over the lazy dog. It was really quick.";

    List<String> finalTokens =
        Stream.of(raw.split("\\W+"))
            .map(String::toUpperCase)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

    System.out.println(finalTokens);
  }
}