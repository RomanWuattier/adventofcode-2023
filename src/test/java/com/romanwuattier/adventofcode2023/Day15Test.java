package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(508498, 279116), new Day15().test());
  }
}
