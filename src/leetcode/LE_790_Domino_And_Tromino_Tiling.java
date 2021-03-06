package leetcode;

/**
 * Created by yuank on 11/11/18.
 */
public class LE_790_Domino_And_Tromino_Tiling {
    /**
         We have two types of tiles: a 2x1 domino shape, and an "L" tromino shape.
         These shapes may be rotated.

         XX  <- domino

         XX  <- "L" tromino
         X
         Given N, how many ways are there to tile a 2 x N board? Return your answer modulo 10^9 + 7.

         (In a tiling, every square must be covered by a tile. Two tilings are different if and only
         if there are two 4-directionally adjacent cells on the board such that exactly one of the
         tilings has both squares occupied by a tile.)

         Example:
         Input: 3
         Output: 5
         Explanation:
         The five different ways are listed below, different letters indicates different tiles:
         XYZ XXZ XYY XXY XYY
         XYZ YYZ XZZ XYY XXY
         Note:

         N  will be in range [1, 1000].

         Medium
     */

    /**
         DP Solution 1
         https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-790-domino-and-tromino-tiling/

         dp[i][0] : ways to cover i cols, fully covered ||
         dp[i][1] : ways to cover i cols, not fully covered at upper left
         dp[i][2] : ways to cover i cols, not fully covered at lower right

         add '|'        add '||'        add 'L'       add 'L'
         dp[i][0] = dp[i - 1][0] + dp[i - 2][0] + dp[i - 1][1] + dp[i - 1][2];
         dp[i][1] = dp[i - 2][0] + dp[i - 1][2]
         dp[i][2] = dp[i - 2][0] + dp[i - 1][1]

         dp[i][1] and dp[i][2] is equal:

         dp[i][0] = dp[i - 1][0] + dp[i - 2][0] + 2 * dp[i - 1][1]
         dp[i][i] = dp[i - 2][0] + dp[i - 1][1]

         dp[0][0] = 1
         dp[0][1] = 1
     **/
    public int numTilings1(int N) {
        if (N <= 0) {
            return 0;
        }

        int mod = 1000000007;

        //!!! use long to avoid overflow
        long[][] dp = new long[N + 1][3];
        dp[0][0] = 1;
        dp[1][0] = 1;

        for (int i = 2; i <= N; i++) {
            dp[i][0] = (dp[i - 1][0] + dp[i - 2][0] + dp[i - 1][1] + dp[i - 1][2]) % mod;
            dp[i][1] = (dp[i - 2][0] + dp[i - 1][2]) % mod;
            dp[i][2] = (dp[i - 2][0] + dp[i - 1][1]) % mod;
        }

        return (int)dp[N][0];
    }

    /**
     * DP Solution 2
     * https://leetcode.com/problems/domino-and-tromino-tiling/discuss/116581/Detail-and-explanation-of-O(n)-solution-why-dpn2*dn-1+dpn-3
     */
    int numTilings2(int N) {
        int md = 1000000007;
        long[] dp = new long[1001];

        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 5;
        if (N <= 3) {
            return (int)dp[N];
        }
        for (int i = 4; i <= N; ++i) {
            dp[i] = 2 * dp[i - 1] + dp[i - 3];
            dp[i] %= md;
        }
        return (int)dp[N];
    }
}

