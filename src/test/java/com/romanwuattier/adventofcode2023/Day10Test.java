package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
  @Test
  void test() {
    assertEquals(new Output<>(6890, null), new Day10().test());
  }
}