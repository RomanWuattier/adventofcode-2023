package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 implements Day {
  public static void main(String[] args) {
    new Day11().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(11).map(l -> Arrays.stream(l.split("")).collect(Collectors.toList()))
        .collect(Collectors.toList());
    long p1 = get(lines, 1);
    long p2 = get(lines, 1_000_000 - 1);
    return new Output<>(p1, p2);
  }

  private long get(List<List<String>> map, int mult) {
    int length = map.size();
    int width = map.get(0).size();

    var extraRows = new int[length];
    var extraCols = new int[width];
    for (int r = 1; r < length; r++) {
      extraRows[r] = extraRows[r - 1] + 1;
      if (!map.get(r).contains("#")) {
        extraRows[r] += mult;
      }
    }
    for (int c = 1; c < width; c++) {
      var col = new StringBuilder();
      for (int r = 0; r < length; r++) {
        col.append(map.get(r).get(c));
      }
      extraCols[c] = extraCols[c - 1] + 1;
      if (!col.toString().contains("#")) {
        extraCols[c] += mult;
      }
    }

    var galaxies = new ArrayList<long[]>();
    for (int r = 0; r < length; r++) {
      for (int c = 0; c < width; c++) {
        if (map.get(r).get(c).equals("#")) {
          galaxies.add(new long[]{extraRows[r], extraCols[c]});
        }
      }
    }

    long out = 0;
    for (int i = 0; i < galaxies.size(); i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        var g1 = galaxies.get(i);
        var g2 = galaxies.get(j);
        out += Math.abs(g1[0] - g2[0]) + Math.abs(g1[1] - g2[1]);
      }
    }
    return out;
  }
}
