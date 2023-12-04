package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 implements Day {
  public static void main(String[] args) {
    new Day4().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(4).toList();

    int p1 = 0;
    var matches = new int[lines.size()];

    for (int ln = 0; ln < lines.size(); ln++) {
      var cards = Arrays.stream(lines.get(ln).split(" ")).filter(s -> !s.isBlank()).toList();
      var winning = IntStream.range(2, 12).mapToObj(i -> Integer.parseInt(cards.get(i))).collect(Collectors.toSet());
      int winningNum = (int) IntStream.range(13, cards.size())
          .filter(i -> winning.contains(Integer.parseInt(cards.get(i))))
          .count();
      p1 += 1 << (winningNum - 1);

      int copies = ++matches[ln];
      IntStream.rangeClosed(ln + 1, ln + winningNum).forEach(i -> matches[i] += copies);
    }
    int p2 = Arrays.stream(matches).sum();
    return new Output<>(p1, p2);
  }
}
