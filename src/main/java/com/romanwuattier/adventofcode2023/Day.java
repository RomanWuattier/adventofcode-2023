package com.romanwuattier.adventofcode2023;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.stream.Stream;

public interface Day {
  Output<?, ?> out();

  default void run() {
    var out = out();
    System.out.printf("""
      Part 1: %s
      Part 2: %s
      """, out.p1, out.p2);
  }

  /** test helper */
  default Output<?, ?> test() {
    return out();
  }

  record Output<T, U>(T p1, U p2) {}

  default Stream<String> readDay(int day) {
    return streamLines(
        new File(
            Objects.requireNonNull(Day.class.getClassLoader().getResource("day" + day + ".txt"))
                .getFile()));
  }

  private Stream<String> streamLines(File file) {
    try {
      return Files.lines(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  default String readDayAsString(int day) {
    return getFileAsString(
        new File(
            Objects.requireNonNull(Day.class.getClassLoader().getResource("day" + day + ".txt"))
                .getFile()));
  }

  private String getFileAsString(File file) {
    try {
      return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
