package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 implements Day {
  public static void main(String[] args) {
    new Day13().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(13).toList();
    var patterns = new ArrayList<List<String>>();
    var tmp = new ArrayList<String>();
    lines.forEach(l -> {
      if (l.isBlank()) {
        patterns.add(new ArrayList<>(tmp));
        tmp.clear();
      } else {
        tmp.add(l.strip());
      }
    });
    patterns.add(new ArrayList<>(tmp));

    int p1 = 0, p2 = 0;
    for (var p : patterns) {
      p1 += 100 * findHorizontalReflection(p, 0) + findVerticalReflection(p, 0);
      p2 += 100 * findHorizontalReflection(p, 1) + findVerticalReflection(p, 1);
    }
    return new Output<>(p1, p2);
  }

  private int findHorizontalReflection(List<String> pattern, int smudge) {
    for (int i = 0; i < pattern.size() - 1; i++) {
      int j = 0, diff = 0;
      while (i - j >= 0 && i + j + 1 < pattern.size()) {
        diff += accDiff(pattern.get(i - j), pattern.get(i + j + 1));
        if (diff > smudge) {
          break;
        }
        j++;
      }
      if (diff == smudge) {
        return i + 1;
      }
    }
    return 0;
  }

  private int findVerticalReflection(List<String> pattern, int smudge) {
    for (int c = 0; c < pattern.get(0).length() - 1; c++) {
      int j = 0, diff = 0;
      while (c - j >= 0 && c + j + 1 < pattern.get(0).length()) {
        diff += accDiff(getCol(pattern, c - j), getCol(pattern, c + j + 1));
        if (diff > smudge) {
          break;
        }
        j++;
      }
      if (diff == smudge) {
        return c + 1;
      }
    }
    return 0;
  }

  private String getCol(List<String> pattern, int col) {
    return pattern.stream().map(s -> String.valueOf(s.charAt(col))).collect(Collectors.joining());
  }

  private int accDiff(String a, String b) {
    return (int) IntStream.range(0, a.length()).filter(i -> a.charAt(i) != b.charAt(i)).count();
  }
}
