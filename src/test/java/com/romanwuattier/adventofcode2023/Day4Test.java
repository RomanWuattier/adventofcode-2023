package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(20829, 12648035), new Day4().test());
  }
}
