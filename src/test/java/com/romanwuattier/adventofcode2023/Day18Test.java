package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {
  @Test
  void test() {
    assertEquals(new Output<>(28911L, 77366737561114L), new Day18().test());
  }
}