package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
  @Test
  void test() {
    assertEquals(new Output<>(489392, null), new Day19().test());
  }
}