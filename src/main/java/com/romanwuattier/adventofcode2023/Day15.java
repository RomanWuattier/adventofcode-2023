package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 implements Day {
  public static void main(String[] args) {
    new Day15().run();
  }

  @Override
  public Output<?, ?> out() {
    var line = readDay(15).toList().get(0).strip();
    var sequence = Arrays.stream(line.strip().split(",")).map(s -> s.chars().toArray()).toList();
    int p1 = sequence.stream().mapToInt(this::hash).sum();
    int p2 = p2(sequence);
    return new Output<>(p1, p2);
  }

  private int p2(List<int[]> sequence) {
    LinkedHashMap<String, Integer>[] boxes = new LinkedHashMap[256];
    for (int[] seq : sequence) {
      var op = Arrays.stream(seq).filter(code -> (int) '=' == code || (int) '-' == code).findFirst();
      var label = Arrays.stream(seq).takeWhile(code -> code != '=' && code != '-').toArray();
      var box = hash(label);
      var key = Arrays.stream(label).mapToObj(String::valueOf).collect(Collectors.joining(":"));
      if (op.isEmpty()) {
        throw new IllegalStateException();
      }
      if (op.getAsInt() == '-') {
        if (boxes[box] != null) {
          boxes[box].remove(key);
        }
      } else if (op.getAsInt() == '=') {
        var length = Character.getNumericValue(seq[seq.length - 1]);
        if (boxes[box] == null) {
          boxes[box] = new LinkedHashMap<>();
        }
        boxes[box].put(key, length);
      }
    }

    return IntStream.range(0, 256)
        .filter(i -> boxes[i] != null)
        .map(i -> {
          int out = 0, j = 0;
          for (var length : boxes[i].values()) {
            out += (i + 1) * (j++ + 1) * length;
          }
          return out;
        })
        .sum();
  }

  private int hash(int[] codes) {
    int out = 0;
    for (int c : codes) {
      out = (out + c) * 17 % 256;
    }
    return out;
  }
}
