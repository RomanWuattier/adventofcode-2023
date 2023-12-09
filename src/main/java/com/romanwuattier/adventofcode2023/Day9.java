package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Day9 implements Day {
  public static void main(String[] args) {
    new Day9().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(9).toList();
    int p1 = 0, p2 = 0;
    for (var line : lines) {
      p1 += predict(line, __ -> {});
      p2 += predict(line, Collections::reverse);
    }
    return new Output<>(p1, p2);
  }

  private int predict(String line, Consumer<List<Integer>> predictionEffect) {
    var predictions = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
    predictionEffect.accept(predictions);
    int out = 0;
    while (!predictions.stream().allMatch(i -> i == 0)) {
      out += predictions.get(predictions.size() - 1);
      var next = new ArrayList<Integer>();
      for (int i = 0; i < predictions.size() - 1; i++) {
        next.add(predictions.get(i + 1) - predictions.get(i));
      }
      predictions = next;
    }
    return out;
  }
}