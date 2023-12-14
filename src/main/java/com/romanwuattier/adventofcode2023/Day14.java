package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Day14 implements Day {
  public static void main(String[] args) {
    new Day14().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(14).map(l -> l.strip().split("")).toArray(size -> new String[size][1]);
    var clone = copy(lines);
    moveNorth(clone);
    int p1 = calcLoad(clone);
    int p2 = p2(copy(lines));
    return new Output<>(p1, p2);
  }

  private int p2(String[][] grid) {
    var cache = new HashMap<String, Integer>();
    var reversedCache = new HashMap<Integer, String[][]>();
    int start, period;
    for (int i = 0; true; i++) {
      grid = cycle(grid);
      var key = key(grid);
      if (cache.containsKey(key)) {
        start = cache.get(key);
        period = i - start;
        break;
      }
      cache.put(key, i);
      reversedCache.put(i, copy(grid));
    }
    return calcLoad(reversedCache.get((1000000000 - start) % period + start - 1));
  }

  private String[][] copy(String[][] grid) {
    return Arrays.stream(grid).map(String[]::clone).toArray(String[][]::new);
  }

  private String key(String[][] grid) {
    return Arrays.stream(grid).map(arr -> String.join("", arr)).collect(Collectors.joining());
  }

  private void moveNorth(String[][] grid) {
    for (int r = 1; r < grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        for (int k = r - 1; k >= 0; k--) {
          if (grid[k][c].equals(".") && grid[k + 1][c].equals("O")) {
            grid[k][c] = "O";
            grid[k + 1][c] = ".";
          }
        }
      }
    }
  }

  private int calcLoad(String[][] grid) {
    int out = 0;
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        if (grid[r][c].equals("O")) {
          out += grid.length - r;
        }
      }
    }
    return out;
  }

  private String[][] cycle(String[][] grid) {
    // north
    moveNorth(grid);
    // west
    grid = rotate(grid);
    moveNorth(grid);
    // south
    grid = rotate(grid);
    moveNorth(grid);
    // east
    grid = rotate(grid);
    moveNorth(grid);

    return rotate(grid);
  }

  private String[][] rotate(String[][] grid) {
    int rows = grid.length;
    int cols = grid[0].length;
    var rotated = new String[cols][rows];
    for (int c = 0; c < cols; c++) {
      for (int r = 0; r < rows; r++) {
        rotated[c][r] = grid[rows - r - 1][c];
      }
    }
    return rotated;
  }
}
