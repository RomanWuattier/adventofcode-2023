package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25Test {
  @Test
  void test() {
    assertEquals(new Output<>(545528, "no part 2"), new Day25().test());
  }
}