package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(9233514L, 363293506944L), new Day11().test());
  }
}
