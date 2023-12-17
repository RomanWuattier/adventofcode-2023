package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Day17 implements Day {
  public static void main(String[] args) {
    new Day17().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(17).toList();
    var grid = lines.stream()
        .map(l -> Arrays.stream(l.strip().split("")).mapToInt(Integer::parseInt).toArray())
        .toArray(size -> new int[size][1]);
    int p1 = dijkstra(grid, 0, 3);
    int p2 = dijkstra(grid, 4, 10);
    return new Output<>(p1, p2);
  }

  private int dijkstra(int[][] g, int minStep, int maxStep) {
    int height = g.length, width = g[0].length;
    var seen = new HashSet<String>();
    var dirs = new Dir[]{Dir.E, Dir.S, Dir.W, Dir.N};
    var q = new PriorityQueue<Item>(Comparator.comparingInt(i -> i.loss)); // kind of a Dijkstra with a priority queue
    q.offer(new Item(0, 0, 1, 0, Dir.E));
    q.offer(new Item(0, 0, 1, 0, Dir.S));

    while (!q.isEmpty()) {
      var item = q.remove();
      var key = item.r + ":" + item.c + ":" + item.dir + ":" + item.step;
      if (seen.contains(key)) {
        continue;
      }
      seen.add(key);
      if (item.r == height - 1 && item.c == width - 1 && item.step >= minStep) {
        return item.loss;
      }

      IntStream.range(0, dirs.length)
          .filter(i -> dirs[(i + 2) % 4] != item.dir) // no reverse
          .filter(i -> (item.step < maxStep || dirs[i] != item.dir) && (item.step >= minStep || dirs[i] == item.dir))
          .mapToObj(i -> dirs[i])
          .forEach(dir -> {
            int dr = item.r + dir.i;
            int dc = item.c + dir.j;
            if (dr >= 0 && dr < height && dc >= 0 && dc < width) {
              int nextLoss = item.loss + g[dr][dc];
              int nextStep = item.dir == dir ? item.step + 1 : 1;
              q.offer(new Item(dr, dc, nextStep, nextLoss, dir));
            }
          });
    }
    throw new IllegalStateException();
  }

  record Item(int r, int c, int step, int loss, Dir dir) {
  }

  private record Dir(int i, int j) {
    private static final Dir N = new Dir(-1, 0);
    private static final Dir S = new Dir(1, 0);
    private static final Dir E = new Dir(0, 1);
    private static final Dir W = new Dir(0, -1);
  }
}
