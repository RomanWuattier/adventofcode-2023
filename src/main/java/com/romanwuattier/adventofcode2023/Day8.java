package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day8 implements Day {
  public static void main(String[] args) {
    new Day8().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(8).toList();
    var inst = Arrays.stream(lines.get(0).split("")).toList();
    var g = IntStream.range(2, lines.size()).mapToObj(lines::get).map(s -> {
          var arr = s.replace(" ", "").replace("(", "").replace(")", "").split("=");
          var nodes = arr[1].split(",");
          return new String[]{arr[0], nodes[0], nodes[1]};
        })
        .collect(Collectors.toMap(arr -> arr[0], arr -> new Node(arr[1], arr[2])));

    var p1 = bfs(g, inst, List.of("AAA"), s -> s.equals("ZZZ")).stream().findFirst().orElseThrow();
    var p2 = bfs(g, inst, g.keySet().stream().filter(s -> s.endsWith("A")).toList(), s -> s.endsWith("Z")).stream()
        .mapToLong(i -> i)
        .reduce(this::lcm)
        .orElseThrow();
    return new Output<>(p1, p2);
  }

  private List<Integer> bfs(Map<String, Node> g, List<String> inst, List<String> roots, Predicate<String> target) {
    var out = new ArrayList<Integer>();
    for (var node : roots) {
      int steps = 0, i = 0;
      while (!target.test(node)) {
        if (i >= inst.size()) {
          i = 0;
        }
        var next = g.get(node);
        if (inst.get(i).equals("L")) {
          node = next.l;
        } else if (inst.get(i).equals("R")) {
          node = next.r;
        } else {
          throw new IllegalStateException();
        }
        steps++;
        i++;
      }
      out.add(steps);
    }
    return out;
  }

  private long lcm(long a, long b) {
    if (a == 0 || b == 0) {
      return 0;
    }
    a = Math.abs(a);
    b = Math.abs(b);
    long max = Math.max(a, b);
    long min = Math.min(a, b);
    long lcm = max;
    while (lcm % min != 0) {
      lcm += max;
    }
    return lcm;
  }

  private record Node(String l, String r) {
  }
}
