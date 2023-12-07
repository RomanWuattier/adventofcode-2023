package com.romanwuattier.adventofcode2023;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class Day7 implements Day {
  public static void main(String[] args) {
    new Day7().run();
  }

  @Override
  public Output<?, ?> out() {
    var lines = readDay(7).toList();

    var strength = new HashMap<Character, Integer>();
    strength.putAll(Map.of('A', 14, 'K', 13, 'Q', 12, 'J', 11, 'T', 10));
    strength.putAll(Map.of('9', 9, '8', 8, '7', 7, '6', 6, '5', 5, '4', 4, '3', 3, '2', 2));

    Comparator<Hand> comp = (h1, h2) -> {
      if (h1.rank > h2.rank) {
        return 1;
      } else if (h1.rank < h2.rank) {
        return -1;
      }
      for (int i = 0; i < h1.cards.length(); i++) {
        if (h1.cards.charAt(i) == h2.cards.charAt(i)) {
          continue;
        }
        if (strength.get(h1.cards.charAt(i)) > strength.get(h2.cards.charAt(i))) {
          return 1;
        } else {
          return -1;
        }
      }
      return 0;
    };

    var p1 = winnings(lines, false, comp);

    // Set J as the weakest card for p2
    strength.put('J', 0);

    var p2 = winnings(lines, true, comp);
    return new Output<>(p1, p2);
  }

  private int winnings(List<String> lines, boolean hasJoker, Comparator<Hand> c) {
    var hands = new PriorityQueue<>(c);
    for (var line : lines) {
      if (line.isBlank()) {
        break;
      }
      var arr = line.split(" ");
      hands.offer(rankHand(arr[0], Integer.parseInt(arr[1]), hasJoker));
    }
    int out = 0;
    int rank = 1;
    while (!hands.isEmpty()) {
      out += hands.poll().bid * rank;
      rank++;
    }
    return out;
  }

  private Hand rankHand(String cards, int bid, boolean hasJoker) {
    var map = new HashMap<Character, Integer>();
    for (int i = 0; i < cards.length(); i++) {
      char c = cards.charAt(i);
      map.put(c, map.getOrDefault(c, 0) + 1);
    }

    Optional<Integer> joker = hasJoker ? Optional.ofNullable(map.get('J')) : Optional.empty();

    var max = map.values().stream().mapToInt(i -> i).max().orElse(0);
    var rank = switch (map.size()) {
      case 1 -> Hand.FiveOfAKind;
      case 2 -> {
        if (joker.isPresent()) {
          yield Hand.FiveOfAKind;
        }
        yield max == 4 ? Hand.FourOfAKind : Hand.FullHouse;
      }
      case 3 -> {
        if (max == 3) {
          yield joker.isPresent() ? Hand.FourOfAKind : Hand.ThreeOfAKind;
        } else {
          if (joker.isPresent()) {
            yield joker.get() == 2 ? Hand.FourOfAKind : Hand.FullHouse;
          }
          yield Hand.TwoPairs;
        }
      }
      case 4 -> joker.isPresent() ? Hand.ThreeOfAKind : Hand.OnePair;
      default -> joker.isPresent() ? Hand.OnePair : Hand.HighCard;
    };
    return new Hand(cards, bid, rank);
  }

  record Hand(String cards, int bid, int rank) {
    private static final int FiveOfAKind = 7;
    private static final int FourOfAKind = 6;
    private static final int FullHouse = 5;
    private static final int ThreeOfAKind = 4;
    private static final int TwoPairs = 3;
    private static final int OnePair = 2;
    private static final int HighCard = 1;
  }
}
