package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(11911, 10151663816849L), new Day8().test());
  }
}
