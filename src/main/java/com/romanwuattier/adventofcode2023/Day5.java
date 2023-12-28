package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    long p1 = Arrays.stream(seeds).map(seed -> process(seed, maps)).min().orElseThrow();

    var seedsRange = IntStream.range(0, seeds.length)
        .filter(i -> i % 2 == 0)
        .mapToObj(i -> new Range(seeds[i], seeds[i] + seeds[i + 1] - 1, 0))
        .toList();
    for (var map : maps) {
      seedsRange = process(seedsRange, map);
    }
    long p2 = seedsRange.stream().mapToLong(r -> r.src).min().orElseThrow();
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

  private List<Range> process(List<Range> seeds, TreeMap<Long, Range> map) {
    var q = new LinkedList<>(seeds);
    var out = new ArrayList<Range>();

    while (!q.isEmpty()) {
      var seed = q.remove();
      long seedStart = seed.src;
      long seedEnd = seed.dst;
      Optional.ofNullable(map.floorEntry(seedStart)).ifPresentOrElse(floor -> {
        long srcOverlap = Math.max(seedStart, floor.getValue().src);
        long dstOverlap = Math.min(seedEnd, floor.getValue().src + floor.getValue().offset - 1);
        if (dstOverlap > srcOverlap) {
          if (seedStart < srcOverlap) {
            q.offer(new Range(seedStart, srcOverlap - 1, 0));
          }
          if (seedEnd > dstOverlap) {
            q.offer(new Range(dstOverlap + 1, seedEnd, 0));
          }
          long offset = srcOverlap - floor.getValue().src;
          out.add(new Range(floor.getValue().dst + offset,
              floor.getValue().dst + offset + (dstOverlap - srcOverlap + 1), 0));
        } else {
          out.add(new Range(seedStart, seedEnd, 0));
        }
      }, () -> out.add(new Range(seedStart, seedEnd, 0)));
    }
    return out;
  }

  record Range(long src, long dst, long offset) {
  }
}
