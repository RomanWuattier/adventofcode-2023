package com.romanwuattier.adventofcode2023;

import com.romanwuattier.adventofcode2023.Day.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
  @Test
  void name() {
    assertEquals(new Output<>(278755257L, 26829166L), new Day5().test());
  }
}