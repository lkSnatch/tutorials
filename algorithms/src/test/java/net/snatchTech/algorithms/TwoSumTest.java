package net.snatchTech.algorithms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

class TwoSumTest {

  private TwoSum twoSum = new TwoSum();

  static Stream<Arguments> numberArrayProvider() {
    return Stream.of(
        Arguments.of(new int[]{1, 2, 5, 7, 3, 11, 4, 15}, 10, new int[]{3,4}),
        Arguments.of(new int[]{4,4}, 8, new int[]{0,1})
    );
  }

  @ParameterizedTest
  @MethodSource("numberArrayProvider")
  void bruteForce(int[] numbers, int target, int[] answer) {
    int[] actual = twoSum.bruteForce(numbers, target);
    assertArrayEquals(answer, actual,
                      "Wrong answer! Should be %s, but actual is %s".formatted(Arrays.toString(answer), Arrays.toString(actual)));
  }

  @ParameterizedTest
  @MethodSource("numberArrayProvider")
  void hashTable(int[] numbers, int target, int[] answer) {
    int[] actual = twoSum.hashTable(numbers, target);
    assertArrayEquals(answer, actual,
                      "Wrong answer! Should be %s, but actual is %s".formatted(Arrays.toString(answer), Arrays.toString(actual)));
  }
}