package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day21 implements Day {
  public static void main(String[] args) {
    new Day21().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(21).toList();
    var grid = lines.stream()
        .map(l -> Arrays.stream(l.strip().split("")).toArray(String[]::new))
        .toArray(size -> new String[size][1]);

    var distances = bfs(grid, findStart(grid));
    long p1 = distances.values().stream().filter(i -> i <= 64 && i % 2 == 0).count();

    // Credit: https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
    long evenCorners = distances.values().stream().filter(i -> i > 65 && i % 2 == 0).count();
    long oddCorners = distances.values().stream().filter(i -> i > 65 && i % 2 == 1).count();
    long evenSquares = distances.values().stream().filter(i -> i  % 2 == 0).count();
    long oddSquares = distances.values().stream().filter(i -> i  % 2 == 1).count();
    long nSquare = (26501365 - 65) / 131;
    long p2 = ((nSquare + 1) * (nSquare + 1)) * oddSquares + (nSquare * nSquare) * evenSquares - (nSquare + 1) * oddCorners + nSquare * evenCorners;
    return new Output<>(p1, p2);
  }

  private Point findStart(String[][] g) {
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[i].length; j++) {
        if (g[i][j].equals("S")) {
          return new Point(i, j);
        }
      }
    }
    throw new IllegalStateException();
  }

  private Map<Point, Integer> bfs(String[][] g, Point start) {
    int h = g.length, w = g[0].length;
    var distances = new HashMap<Point, Integer>();
    distances.put(start, 0);
    var q = new LinkedList<Point>();
    q.offer(start);
    while (!q.isEmpty()) {
      var p = q.remove();
      var neighs = p.getNeighs(h, w);
      int dist = distances.get(p);
      for (var neigh : neighs) {
        int nDist = dist + 1;
        if (!g[neigh.x][neigh.y].equals("#") && nDist < distances.getOrDefault(neigh, Integer.MAX_VALUE)) {
          distances.put(neigh, nDist);
          q.offer(neigh);
        }
      }
    }
    return distances;
  }

  private record Point(int x, int y) {
    private List<Point> getNeighs(int h, int w) {
      int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
      var neigh = new ArrayList<Point>();
      Arrays.stream(dirs).forEach(d -> {
        int dx = this.x + d[0], dy = this.y + d[1];
        if (dx >= 0 && dx < h && dy >= 0 && dy < w) {
          neigh.add(new Point(dx, dy));
        }
      });
      return neigh;
    }
  }
}
