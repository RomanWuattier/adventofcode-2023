package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {
  @Test
  void test() {
    assertEquals(new Output<>(7361L, 83317216247365L), new Day12().test());
  }
}
