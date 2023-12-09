package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {
  @Test
  void test() {
    assertEquals(new Output<>(1938800261, 1112), new Day9().test());
  }
}