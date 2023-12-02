package com.romanwuattier.adventofcode2023;

import java.util.function.Function;

public class Day2 implements Day {
  public static void main(String[] args) {
    new Day2().run();
  }

  @Override
  public Output<?, ?> out() {
    final Function<String[], Output<Boolean, Integer>> f = arr -> {
      int r = 12, g = 13, b = 14;
      boolean isPossible = true;
      int rr = 1, gg = 1, bb = 1;
      for (int i = 3; i < arr.length; i += 2) {
        int val = Integer.parseInt(arr[i - 1]);
        if (arr[i].startsWith("blue")) {
          isPossible &= val <= b;
          bb = Math.max(bb, val);
        } else if (arr[i].startsWith("red")) {
          isPossible &= val <= r;
          rr = Math.max(rr, val);
        } else if (arr[i].startsWith("green")) {
          isPossible &= val <= g;
          gg = Math.max(gg, val);
        }
      }
      return new Output<>(isPossible, rr * gg * bb);
    };

    var lines = readDay(2).toList();
    int p1 = 0, p2 = 0;
    for (var l : lines) {
      var arr = l.split(" ");
      var pair = f.apply(arr);
      if (pair.p1()) {
        p1 += Integer.parseInt(arr[1].replace(":", ""));
      }
      p2 += pair.p2();
    }
    return new Output<>(p1, p2);
  }
}