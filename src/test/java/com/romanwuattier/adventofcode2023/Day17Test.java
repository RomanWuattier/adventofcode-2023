package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {
  @Test
  void test() {
    assertEquals(new Output<>(686, 801), new Day17().test());
  }
}