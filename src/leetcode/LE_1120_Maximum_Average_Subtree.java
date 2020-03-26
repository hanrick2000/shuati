package leetcode;

import common.TreeNode;

public class LE_1120_Maximum_Average_Subtree {
    /**
     * Given the root of a binary tree, find the maximum average value of any subtree of that tree.
     *
     * (A subtree of a tree is any node of that tree plus all its descendants. The average value
     * of a tree is the sum of its values, divided by the number of nodes.)
     *
     * Note:
     *
     * The number of nodes in the tree is between 1 and 5000.
     * Each node will have a value between 0 and 100000.
     * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
     *
     * Medium
     */

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    class Solution {
        class Pair {
            int sum;
            int count;

            public Pair(int sum, int count) {
                this.sum = sum;
                this.count = count;
            }
        }

        double res = Double.MIN_VALUE;

        public double maximumAverageSubtree(TreeNode root) {
            if (root == null) return 0.0;

            dfs(root);

            return res;
        }

        private Pair dfs(TreeNode root) {
            if (root == null)  {
                return new Pair(0, 0);
            }

            Pair p1 = dfs(root.left);
            Pair p2 = dfs(root.right);

            int sum = root.val + p1.sum + p2.sum;
            int count = 1 + p1.count + p2.count;

            res = Math.max(res, (double)sum / (double)count);

            return new Pair(sum, count);
        }
    }
}
