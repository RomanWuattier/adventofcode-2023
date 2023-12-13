package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(33728, 28235), new Day13().test());
  }
}
