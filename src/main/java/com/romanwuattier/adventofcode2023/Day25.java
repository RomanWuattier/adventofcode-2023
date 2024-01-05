package com.romanwuattier.adventofcode2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Day25 implements Day {
  public static void main(String[] args) {
    new Day25().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(25).toList();
    var g = Graph.from(lines);
    while (g.kargerMinCut() != 3) {
      g = Graph.from(lines);
    }
    return new Output<>(g.multiplyGroupsSize(), "no part 2");
  }

  private record Graph(Map<Vertex, List<Vertex>> vertices) {
    private static Graph from(List<String> lines) {
      var v = new HashMap<Vertex, List<Vertex>>();
      lines.forEach(l -> {
        var root = new Vertex(l.substring(0, l.indexOf(":")).trim());
        var children = l.substring(l.indexOf(":") + 2).split(" ");
        v.putIfAbsent(root, new ArrayList<>());
        Arrays.stream(children).map(child -> new Vertex(child.trim())).forEach(child -> {
          v.putIfAbsent(child, new ArrayList<>());
          v.get(root).add(child);
          v.get(child).add(root);
        });
      });
      return new Graph(v);
    }

    // Run Karger algorithm https://en.wikipedia.org/wiki/Karger%27s_algorithm
    private int kargerMinCut() {
      var copy = new HashMap<>(vertices);
      while (vertices.keySet().size() > 2) {
        var rand = new Random();
        int r = rand.nextInt(vertices.keySet().size());
        var from = vertices.keySet().toArray(new Vertex[0])[r];
        r = rand.nextInt(vertices.get(from).size());
        var to = vertices.get(from).get(r);
        cut(from, to);
      }

      var l1 = vertices.keySet().toArray(new Vertex[0])[0].label;
      var l2 = vertices.keySet().toArray(new Vertex[0])[1].label;
      int cuts = 0;
      for (var v1 : l1.split(":")) {
        var vertices = copy.get(new Vertex(v1));
        for (var v2 : l2.split(":")) {
          if (vertices.contains(new Vertex(v2))) {
            cuts++;
          }
        }
      }
      for (var v2 : l2.split(":")) {
        var vertices = copy.get(new Vertex(v2));
        for (var v1 : l1.split(":")) {
          if (vertices.contains(new Vertex(v1))) {
            cuts++;
          }
        }
      }
      return cuts;
    }

    private void cut(Vertex from, Vertex to) {
      var combined = new Vertex(from.label + ":" + to.label);
      var children = Stream.of(vertices.remove(from), vertices.remove(to))
          .flatMap(Collection::stream)
          .filter(v -> !v.equals(to) && !v.equals(from))
          .toList();

      vertices.put(combined, new ArrayList<>());
      children.forEach(child -> {
        vertices.get(child).remove(from);
        vertices.get(child).remove(to);

        vertices.get(combined).add(child);
        vertices.get(child).add(combined);
      });
    }

    private int multiplyGroupsSize() {
      return vertices.keySet().toArray(new Vertex[0])[0].label.split(":").length
          * vertices.keySet().toArray(new Vertex[0])[1].label.split(":").length;
    }
  }

  private record Vertex(String label) {
  }
}
