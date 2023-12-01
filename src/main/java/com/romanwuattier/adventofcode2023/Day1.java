package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Day1 implements Day {
  public static void main(String[] args) {
    new Day1().run();
  }

  private static final Map<String, Integer> SPELLED = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5,
      "six", 6, "seven", 7, "eight", 8, "nine", 9);

  @Override
  public Output<?, ?> out() {
    var lines = readDay(1).toList();
    int p1 = lines.stream()
        .map(l -> l.chars().filter(Character::isDigit).map(Character::getNumericValue).toArray())
        .mapToInt(arr -> arr[0] * 10 + arr[arr.length - 1])
        .sum();

    int p2 = lines.stream()
        .mapToInt(l -> {
          var nums = new ArrayList<Integer>();
          int i = 0;
          while (i < l.length()) {
            var spelled = find(l, i);
            if (spelled.isPresent()) {
              nums.add(spelled.get().getValue());
              i += spelled.get().getKey().length() - 1;
            } else if (Character.isDigit(l.charAt(i))) {
              nums.add(Character.getNumericValue(l.charAt(i)));
              i++;
            } else {
              i++;
            }
          }
          return nums.get(0) * 10 + nums.get(nums.size() - 1);
        })
        .sum();
    return new Output<>(p1, p2);
  }

  private Optional<Map.Entry<String, Integer>> find(String l, int i) {
    return SPELLED.entrySet().stream().filter(e -> l.startsWith(e.getKey(), i)).findFirst();
  }
}
