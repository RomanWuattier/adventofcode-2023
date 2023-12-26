package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 implements Day {
  public static void main(String[] args) {
    new Day12().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(12).toList();
    var pattern = lines.stream().map(l -> l.trim().split(" ")[0]).toList();
    var damaged = lines.stream()
        .map(l -> Arrays.stream(l.trim().split(" ")[1].split(",")).map(Integer::parseInt).toList())
        .toList();

    long p1 = IntStream.range(0, pattern.size())
        .mapToLong(i -> dp(pattern.get(i), damaged.get(i), new HashMap<>()))
        .sum();
    long p2 = IntStream.range(0, pattern.size())
        .mapToLong(i -> dp(unfoldPattern(pattern.get(i)), unfoldDamaged(damaged.get(i)), new HashMap<>()))
        .sum();
    return new Output<>(p1, p2);
  }

  private String unfoldPattern(String pattern) {
    var out = IntStream.range(0, 4).mapToObj(i -> pattern + "?").collect(Collectors.joining());
    out += pattern;
    return out;
  }

  private List<Integer> unfoldDamaged(List<Integer> damaged) {
    return IntStream.range(0, 5).mapToObj(i -> damaged).flatMap(Collection::stream).collect(Collectors.toList());
  }

  private long dp(String pattern, List<Integer> damaged, Map<String, Long> mem) {
    var key = pattern + damaged.stream().map(String::valueOf).collect(Collectors.joining());
    if (mem.containsKey(key)) {
      return mem.get(key);
    }
    if (pattern.isBlank()) {
      return damaged.isEmpty() ? 1 : 0;
    }
    char first = pattern.charAt(0);
    long permutations = 0;
    if (first == '.') {
      permutations = dp(pattern.substring(1), damaged, mem);
    } else if (first == '?') {
      permutations = dp("." + pattern.substring(1), damaged, mem) + dp("#" + pattern.substring(1), damaged, mem);
    } else if (!damaged.isEmpty()) {
      int numDamaged = damaged.get(0);
      if (numDamaged <= pattern.length() && pattern.chars().limit(numDamaged).allMatch(c -> c == '?' || c == '#')) {
        damaged = damaged.subList(1, damaged.size());
        if (numDamaged == pattern.length()) {
          permutations = damaged.isEmpty() ? 1 : 0;
        } else if (pattern.charAt(numDamaged) == '.') {
          permutations = dp(pattern.substring(numDamaged + 1), damaged, mem);
        } else if (pattern.charAt(numDamaged) == '?') {
          permutations = dp("." + pattern.substring(numDamaged + 1), damaged, mem);
        }
      }
    }
    mem.put(key, permutations);
    return permutations;
  }
}
