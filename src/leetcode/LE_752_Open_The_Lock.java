package leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LE_752_Open_The_Lock {
    /**
         You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots:
         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'.

         The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'.
         Each move consists of turning one wheel one slot.

         The lock initially starts at '0000', a string representing the state of the 4 wheels.

         You are given a list of deadends dead ends, meaning if the lock displays any of these codes,
         the wheels of the lock will stop turning and you will be unable to open it.

         Given a target representing the value of the wheels that will unlock the lock, return the
         minimum total number of turns required to open the lock, or -1 if it is impossible.

         Example 1:
         Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
         Output: 6
         Explanation:
         A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
         Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
         because the wheels of the lock become stuck after the display becomes the dead end "0102".

         Example 2:
         Input: deadends = ["8888"], target = "0009"
         Output: 1
         Explanation:
         We can turn the last wheel in reverse to move from "0000" -> "0009".

         Example 3:
         Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
         Output: -1
         Explanation:
         We can't reach the target without getting stuck.

         Example 4:
         Input: deadends = ["0000"], target = "8888"
         Output: -1

         Note:
         The length of deadends will be in the range [1, 500].
         target will not be in the list deadends.
         Every string in deadends and the string target will be a string of 4 digits from the 10,000 possibilities '0000' to '9999'.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-752-open-the-lock/
     *
     * Level by level expansion using BFS
     * Each node can have at most 8 neighbors (max branching factor is 8)
     *
     * Number of max states : "0000" - "9999", 10000
     *
     * Time  : O(8 ^ 10000)
     * Space : O(10000 + deadend set size)
     */
    class Solution {
        public int openLock(String[] deadends, String target) {
            if (null == target || target.length() == 0) {
                return 0;
            }

            String start = "0000";
            if (target.equals(start)) {
                return 0;
            }

            Set<String> set = new HashSet<>();
            for (String deadend : deadends) {
                if (target.equals(deadend) || start.equals(deadend)) {
                    return -1;
                }
                set.add(deadend);
            }

            /**
             * or put visited into set (deadend set), they can share the same set.
             */
            Set<String> visited = new HashSet<>();
            visited.add(start);

            Queue<String> q = new LinkedList<>();
            q.offer(start);

            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();
                steps++;

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();

                    for (int j = 0; j < 4; j++) {
                        /**
                         * 坑1：
                         * 从当前数字走一步(转动保险柜数字开关)到下一个数字，只有两种可能：
                         * 当前数字加一，当前数字减一。
                         * 比如，3 -> 3 - 1 = 2, 3 + 1 = 4
                         * 不是从当前数字能到所有其他9个数字。
                         */
                        for (int k = -1; k <= 1; k += 2) {
                            /**
                             * 坑2：
                             * 还是每次变换都做一次toCharArray(),否则变换后恢复的操作不小心会出问题。
                             */
                            char[] chars = cur.toCharArray();

                            /**
                             * 坑3：
                             * 模拟开关向上和向下旋转一位。
                             * "+ 10" ： 0 - 1 = -1, 所以， 0 - 1 + 10 = 9
                             */
                            chars[j] = (char)((chars[j] - '0' + k + 10) % 10 + '0');

                            String next = String.valueOf(chars);

                            if (next.equals(target)) {
                                return steps;
                            }

                            if (set.contains(next) || visited.contains(next)) {
                                continue;
                            }

                            q.offer(next);
                            visited.add(next);
                        }
                    }
                }
            }

            return -1;
        }
    }
}