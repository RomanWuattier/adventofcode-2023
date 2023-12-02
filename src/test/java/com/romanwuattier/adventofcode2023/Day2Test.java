package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(3059, 65371), new Day2().test());
  }
}