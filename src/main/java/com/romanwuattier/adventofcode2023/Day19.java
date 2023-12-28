package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day19 implements Day {
  public static void main(String[] args) {
    new Day19().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(19).toList();
    var workflows =
        lines.stream().takeWhile(l -> !l.isBlank()).map(Workflow::from).collect(Collectors.toMap(w -> w.name, w -> w));
    var ratings = lines.stream().skip(workflows.size() + 1).map(Rating::from).toList();

    return new Output<>(p1(workflows, ratings), null);
  }

  private int p1(Map<String, Workflow> workflows, List<Rating> ratings) {
    int p1 = 0;
    for (var rating : ratings) {
      var w = workflows.get("in");
      while (true) {
        var to = w.apply(rating);
        if (to.equals("A")) {
          p1 += rating.sum();
          break;
        } else if (to.equals("R")) {
          break;
        }
        w = workflows.get(to);
      }
    }
    return p1;
  }

  private record Workflow(String name, List<Op> ops, String to) {
    private static Workflow from(String str) {
      var curly = str.indexOf('{');
      var name = str.substring(0, curly).trim();
      var arr = str.substring(curly + 1, str.length() - 1).trim().split(",");
      var to = "";
      var ops = new ArrayList<Op>();
      for (var condition : arr) {
        if (!condition.contains(":")) {
          to = condition;
        } else {
          ops.add(Op.from(condition));
        }
      }
      return new Workflow(name, ops, to);
    }

    private String apply(Rating rating) {
      return ops.stream().filter(op -> op.p.test(rating)).findFirst().map(op -> op.to).orElse(to);
    }
  }

  private record Op(String category, Predicate<Rating> p, String to) {
    private static Op from(String str) {
      var colon = str.indexOf(":");
      var cat = str.substring(0, 1);
      int val = Integer.parseInt(str.substring(2, colon));
      Predicate<Rating> p = rating -> {
        if (str.contains("<")) {
          return rating.map(cat) < val;
        } else {
          return rating.map(cat) > val;
        }
      };
      return new Op(cat, p, str.substring(colon + 1));
    }
  }

  private record Rating(int x, int m, int a, int s) {
    private static Rating from(String str) {
      int x = 0, m = 0, a = 0, s = 0;
      var arr = str.substring(1, str.length() - 1).trim().split(",");
      for (var rate : arr) {
        if (rate.startsWith("x=")) {
          x = Integer.parseInt(rate.substring(2));
        } else if (rate.startsWith("m=")) {
          m = Integer.parseInt(rate.substring(2));
        } else if (rate.startsWith("a=")) {
          a = Integer.parseInt(rate.substring(2));
        } else if (rate.startsWith("s=")) {
          s = Integer.parseInt(rate.substring(2));
        }
      }
      return new Rating(x, m, a, s);
    }

    private int map(String cat) {
      return switch (cat) {
        case "x" -> this.x;
        case "m" -> this.m;
        case "a" -> this.a;
        case "s" -> this.s;
        default -> throw new IllegalStateException("Unexpected value: " + cat);
      };
    }

    private int sum() {
      return x + m + a + s;
    }
  }
}
