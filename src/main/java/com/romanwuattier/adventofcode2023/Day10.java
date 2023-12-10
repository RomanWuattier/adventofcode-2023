package com.romanwuattier.adventofcode2023;

import java.util.List;
import java.util.Map;

public class Day10 implements Day {
  public static void main(String[] args) {
    new Day10().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(10).toList();
    var grid = lines.stream()
        .map(l -> l.split(""))
        .toArray(size -> new String[size][1]);

    Pair start = null;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j].equals("S")) {
          start = new Pair(i, j);
        }
      }
    }

    var pipes = Map.<String, List<Pair>>of(
        "|", List.of(new Pair(1, 0), new Pair(-1, 0)),
        "-", List.of(new Pair(0, 1), new Pair(0, -1)),
        "L", List.of(new Pair(-1, 0), new Pair(0, 1)),
        "J", List.of(new Pair(-1, 0), new Pair(0, -1)),
        "F", List.of(new Pair(1, 0), new Pair(0, 1)),
        "7", List.of(new Pair(1, 0), new Pair(0, -1)),
        "S", List.of(new Pair(1, 0), new Pair(0, 1), new Pair(-1, 0), new Pair(0, -1)),
        ".", List.of()
    );

    assert start != null;
    int p1 = getLoopLength(grid, pipes, start);

    return new Output<>(p1 / 2 + 1, null);
  }

  private int getLoopLength(String[][] g, Map<String, List<Pair>> pipes, Pair start) {
    int out = 0;
    var prev = start;
    var point = new Pair(start.i, start.j + 1); // based on my input
    while (!g[point.i][point.j].equals("S")) {
      var tmp = next(g, pipes, point, prev);
      prev = point;
      point = tmp;
      out++;
    }
    return out;
  }

  private Pair next(String[][] g, Map<String, List<Pair>> pipes, Pair point, Pair prev) {
    return pipes.getOrDefault(g[point.i][point.j], List.of()).stream()
        .map(p -> new Pair(point.i + p.i, point.j + p.j))
        .filter(p -> pipes.containsKey(g[p.i][p.j]))
        .filter(p -> !p.equals(prev))
        .findFirst().orElseThrow();
  }

  record Pair(int i, int j) {
  }
}
