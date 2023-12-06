package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day6 implements Day {
  public static void main(String[] args) {
    new Day6().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(6).toList();
    var times = Arrays.stream(lines.get(0).split(" ")).filter(s -> !s.isBlank()).dropWhile(s -> !Character.isDigit(s.charAt(0))).toList();
    var distances = Arrays.stream(lines.get(1).split(" ")).filter(s -> !s.isBlank()).dropWhile(s -> !Character.isDigit(s.charAt(0))).toList();

    int p1 = IntStream.range(0, times.size())
        .map(i -> {
          var time = Integer.parseInt(times.get(i));
          var dist = Integer.parseInt(distances.get(i));
          return (int) IntStream.range(1, time).map(t -> t * (time - t)).filter(mm -> mm > dist).count();
        })
        .reduce((l, r) -> l * r).orElse(0);

    var time = Long.parseLong(String.join("", times));
    var dist = Long.parseLong(String.join("", distances));
    int p2 = (int) LongStream.range(1, time).map(t -> t * (time - t)).filter(mm -> mm > dist).count();
    return new Output<>(p1, p2);
  }
}
