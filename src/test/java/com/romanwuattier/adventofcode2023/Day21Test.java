package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test {
  @Test
  void test() {
    assertEquals(new Output<>(3687L, 610321885082978L), new Day21().test());
  }
}