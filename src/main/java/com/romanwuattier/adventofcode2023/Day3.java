package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day3 implements Day {
  public static void main(String[] args) {
    new Day3().run();
  }

  private static final int[][] DIRS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};

  @Override
  public Output<?, ?> out() {
    var lines = readDay(3).toList();

    int p1 = 0, p2 = 0;
    for (int r = 0; r < lines.size(); r++) {
      for (int c = 0; c < lines.get(r).length(); c++) {
        char ch = lines.get(r).charAt(c);
        if (!Character.isDigit(ch) && ch != '.') {
          p1 += getAdjVal(lines, r, c, new HashSet<>(), nums -> nums.stream().mapToInt(i -> i).sum());
          if (ch == '*') {
            p2 += getAdjVal(lines, r, c, new HashSet<>(), nums -> nums.size() == 2 ? nums.get(0) * nums.get(1) : 0);
          }
        }
      }
    }
    return new Output<>(p1, p2);
  }

  private int getAdjVal(List<String> lines, int r, int c, Set<String> mem, Function<List<Integer>, Integer> op) {
    var nums = new ArrayList<Integer>();
    for (var d : DIRS) {
      int dr = r + d[0], dc = c + d[1];
      if (dr >= 0 && dr < lines.size() && dc >= 0 && dc < lines.get(0).length()) {
        char ch = lines.get(dr).charAt(dc);
        if (!mem.contains(dr + ":" + dc) && Character.isDigit(ch)) {
          nums.add(getNum(lines.get(dr), dr, dc, mem));
        }
      }
    }
    return op.apply(nums);
  }

  private int getNum(String line, int r, int c, Set<String> mem) {
    while (c > 0 && Character.isDigit(line.charAt(c - 1))) {
      c--;
    }
    int num = 0;
    for (; c < line.length() && Character.isDigit(line.charAt(c)); c++) {
      char ch = line.charAt(c);
      num = num * 10 + Character.getNumericValue(ch);
      mem.add(r + ":" + c);
    }
    return num;
  }
}
