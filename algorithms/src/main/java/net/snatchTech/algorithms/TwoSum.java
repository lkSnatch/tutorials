package net.snatchTech.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers 'numbers' and an integer 'target',
 * return indices of two numbers from the array which sum equals the target.
 * Each input has exactly one solution.
 * <p>
 * Example 1:
 * Input: numbers = [1,2,5,7,3,11,4,15], target = 10
 * Output: [3,4]
 * Explanation: Because numbers[3] + numbers[4] == 10.
 * <p>
 * Example 2:
 * Input: numbers = [4,4], target = 8
 * Output: [0,1]
 */
public class TwoSum {

  /**
   * Compare each element in a loop with every element in the array.
   * Time complexity: O(N^2);
   * Space Complexity: O(1);
   */
  public int[] bruteForce(int[] numbers, int target) {
    for (int i = 0; i < numbers.length; i++) {
      for (int j = i + 1; j < numbers.length; j++) {
        if (numbers[i] + numbers[j] == target) {
          return new int[]{i, j};
        }
      }
    }
    return new int[0];
  }

  /**
   * Create a hash map, which will contain number as a key and its index in the array as a value.
   * Put number in the map if target - current number in a loop does not exist in the map.
   * In other words if current number in the loop + any number in the map does not equal target.
   * Time complexity: O(N);
   * Space Complexity: O(N);
   */
  public int[] hashTable(int[] numbers, int target) {
    Map<Integer, Integer> numIndexMap = new HashMap<>();
    for (int i = 0; i < numbers.length; i++) {
      if (numIndexMap.containsKey(target - numbers[i])) {
        return new int[]{numIndexMap.get(target - numbers[i]), i};
      }
      numIndexMap.put(numbers[i], i);
    }
    return new int[0];
  }

}
