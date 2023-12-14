package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
  @Test
  void test() {
    assertEquals(new Output<>(105461, 102829), new Day14().test());
  }
}