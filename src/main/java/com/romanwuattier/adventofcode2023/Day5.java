package com.romanwuattier.adventofcode2023;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Day5 implements Day {
  public static void main(String[] args) {
    new Day5().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(5).toList();

    // Example indexes: int[] mapIndexes = new int[]{3, 7, 12, 18, 22, 27, 31};
    int[] mapIndexes = new int[]{3, 50, 72, 100, 141, 187, 225};
    var initialSeeds = lines.get(0).split(" ");
    var seeds = IntStream.range(1, initialSeeds.length).mapToLong(i -> Long.parseLong(initialSeeds[i])).toArray();
    TreeMap<Long, Range>[] maps = new TreeMap[mapIndexes.length];

    for (int i = 0; i < mapIndexes.length; i++) {
      var map = new TreeMap<Long, Range>();
      int mapIdx = mapIndexes[i];
      while (mapIdx < lines.size()) {
        var line = lines.get(mapIdx);
        if (line.isBlank()) {
          break;
        }
        var arr = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
        map.put(arr[1], new Range(arr[1], arr[0], arr[2]));
        mapIdx++;
      }
      maps[i] = map;
    }

    long p1 = Long.MAX_VALUE;
    for (long seed : seeds) {
      p1 = Math.min(p1, process(seed, maps));
    }

    // p2 takes time to complete. It can probably be optimized using Ranges instead of generating the seeds.
    // I will eventually do it when I have time
    long p2 = Long.MAX_VALUE;
    for (int i = 0; i < seeds.length; i += 2) {
      for (long j = seeds[i]; j < seeds[i] + seeds[i + 1]; j++) {
        p2 = Math.min(p2, process(j, maps));
      }
    }
    return new Output<>(p1, p2);
  }

  private long process(long val, TreeMap<Long, Range>[] maps) {
    for (var treeMap : maps) {
      var floor = treeMap.floorEntry(val);
      if (floor != null && floor.getValue().src + floor.getValue().offset - 1 >= val) {
        val = floor.getValue().dst + (val - floor.getValue().src);
      }
    }
    return val;
  }

  record Range(long src, long dst, long offset) {
  }
}
