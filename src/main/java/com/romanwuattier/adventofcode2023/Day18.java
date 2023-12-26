package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day18 implements Day {
  public static void main(String[] args) {
    new Day18().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(18).toList();
    var out = Stream.of(dig(lines, false), dig(lines, true)).map(this::getCubicMeters).toList();
    return new Output<>(out.get(0), out.get(1));
  }

  private List<Point> dig(List<String> lines, boolean part2) {
    var points = new ArrayList<Point>();
    var point = new Point(0, 0);
    points.add(point);
    for (var line : lines) {
      var arr = line.strip().split(" ");
      var dir = part2 ? Point.DIR_MAPPING.get(arr[2].substring(7, 8)) : arr[0];
      var meters = part2 ? Integer.decode(arr[2].substring(1, 7)) : Integer.parseInt(arr[1]);
      point = point.next(dir, meters);
      points.add(point);
    }
    return points;
  }

  private long getCubicMeters(List<Point> corners) {
    return getInteriorPoints(corners) + getTotalManhattanDistance(corners);
  }

  private long getInteriorPoints(List<Point> corners) {
    // Shoelace theorem
    long area = 0;
    for (int i = 0; i < corners.size(); i++) {
      var c1 = corners.get(i);
      var c2 = corners.get(i == corners.size() - 1 ? 0 : i + 1);
      area += (long) c1.x * c2.y - (long) c1.y * c2.x;
    }
    area /= 2;
    // then, apply Pick's theorem: A = i+(b/2)-1; we need to find i
    return area - (getTotalManhattanDistance(corners) / 2) + 1;
  }

  private long getTotalManhattanDistance(List<Point> corners) {
    long total = 0;
    for (int i = 0; i < corners.size(); i++) {
      var c1 = corners.get(i);
      var c2 = corners.get(i == corners.size() - 1 ? 0 : i + 1);
      total += Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }
    return total;
  }

  private record Point(int x, int y) {
    private static final Map<String, String> DIR_MAPPING = Map.of("0", "R", "1", "D", "2", "L", "3", "U");

    private Point next(String dir, int meters) {
      return switch (dir) {
        case "R" -> new Point(this.x + meters, this.y);
        case "L" -> new Point(this.x - meters, this.y);
        case "U" -> new Point(this.x, this.y - meters);
        case "D" -> new Point(this.x, this.y + meters);
        default -> throw new IllegalStateException("Unexpected value: " + dir);
      };
    }
  }
}
