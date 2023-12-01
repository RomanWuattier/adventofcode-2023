package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(55090, 54845), new Day1().test());
  }
}
