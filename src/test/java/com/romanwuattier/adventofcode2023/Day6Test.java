package com.romanwuattier.adventofcode2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
  @Test
  void test() {
    assertEquals(new Day.Output<>(114400, 21039729), new Day6().test());
  }
}
