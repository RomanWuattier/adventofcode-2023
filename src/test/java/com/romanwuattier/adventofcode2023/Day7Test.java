package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(246163188, 245794069), new Day7().test());
  }
}
