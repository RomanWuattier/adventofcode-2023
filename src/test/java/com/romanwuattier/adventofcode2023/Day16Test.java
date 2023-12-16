package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {
  @Test
  void test() {
    assertEquals(new Output<>(6795, 7154), new Day16().test());
  }
}