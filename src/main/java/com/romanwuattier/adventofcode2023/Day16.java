package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Day16 implements Day {
  public static void main(String[] args) {
    new Day16().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(16).toList();
    var grid = lines.stream().map(l -> l.strip().split("")).toArray(size -> new String[size][1]);
    int p1 = bfs(grid, new Point(0, 0, Point.Dir.E));
    int p2 = IntStream.range(0, grid.length).map(i -> {
      int max = bfs(grid, new Point(i, 0, Point.Dir.E));
      max = Math.max(max, bfs(grid, new Point(i, grid[0].length - 1, Point.Dir.W)));
      max = Math.max(max, bfs(grid, new Point(0, i, Point.Dir.S)));
      return Math.max(max, bfs(grid, new Point(grid.length - 1, i, Point.Dir.N)));
    }).max().orElseThrow();
    return new Output<>(p1, p2);
  }

  private int bfs(String[][] g, Point start) {
    int length = g.length, width = g[0].length;
    var mem = new HashSet<Point>();
    var energized = new HashSet<String>();
    var q = new LinkedList<Point>();
    q.offer(start);

    while (!q.isEmpty()) {
      var p = q.remove();
      mem.add(p);
      energized.add(p.x + ":" + p.y);
      var dirs = p.next(g[p.x][p.y]);
      Arrays.stream(dirs).forEach(dir -> {
        int dx = p.x + dir.i, dy = p.y + dir.j;
        var np = new Point(dx, dy, dir);
        if (dx >= 0 && dx < length && dy >= 0 && dy < width && !mem.contains(np)) {
          q.offer(np);
        }
      });
    }
    return energized.size();
  }

  private record Point(int x, int y, Dir dir) {
    Dir[] next(String s) {
      return switch (s) {
        case "/" -> {
          if (dir == Dir.N) yield new Dir[]{Dir.E};
          else if (dir == Dir.S) yield new Dir[]{Dir.W};
          else if (dir == Dir.E) yield new Dir[]{Dir.N};
          else yield new Dir[]{Dir.S};
        }
        case "\\" -> {
          if (dir == Dir.N) yield new Dir[]{Dir.W};
          else if (dir == Dir.S) yield new Dir[]{Dir.E};
          else if (dir == Dir.E) yield new Dir[]{Dir.S};
          else yield new Dir[]{Dir.N};
        }
        case "-" -> {
          if (dir == Dir.N || dir == Dir.S) yield new Dir[]{Dir.W, Dir.E};
          else yield new Dir[]{dir};
        }
        case "|" -> {
          if (dir == Dir.N || dir == Dir.S) yield new Dir[]{dir};
          else yield new Dir[]{Dir.N, Dir.S};
        }
        default -> new Dir[]{dir};
      };
    }

    private record Dir(int i, int j) {
      private static final Dir N = new Dir(-1, 0);
      private static final Dir S = new Dir(1, 0);
      private static final Dir E = new Dir(0, 1);
      private static final Dir W = new Dir(0, -1);
    }
  }
}
