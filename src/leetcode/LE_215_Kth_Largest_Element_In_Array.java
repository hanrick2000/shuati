package leetcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 4/8/18.
 */
public class LE_215_Kth_Largest_Element_In_Array {
    /**
        Find the kth largest element in an unsorted array.
        Note that it is the kth largest element in the sorted order, not the kth distinct element.
        For example,
        Given [3,2,1,5,6,4] and k = 2, return 5.

        Note:
        You may assume k is always valid, 1 ≤ k ≤ array's length.
     **/

    /**
     * Quick Select:
     *
     * Time  : O(n), worst case O(n ^ 2)
     * Space : O(1)
     */
    class Solution_Quick_Select {
        public int findKthLargest(int[] nums, int k) {
            /**
             * !!!
             * All param passed here is index, so to find kth largest number,
             * the target index is k - 1
             */
            return quickSelect(nums, 0, nums.length - 1, k - 1);//!!! index
        }

        private int quickSelect(int[] nums, int start, int end, int k) {
            if (start >= end) return nums[start];

            int l = start;
            int r = end;

            int pivot = nums[start + (end - start) / 2];

            while (l <= r) {
                while (l <= r && nums[l] > pivot) {
                    l++;
                }

                while (l <= r && nums[r] < pivot) {
                    r--;
                }

                if (l <= r) {
                    swap(nums, l, r);
                    l++;
                    r--;
                }
            }

            if (k <= r) {
                return quickSelect(nums, start, r, k);
            }

            if (k >= l) {
                return quickSelect(nums, l, end, k );
            }

            return nums[k];
        }

        private void swap(int[] nums, int left, int right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }

    /**
        Solution 1 : use min heap.

        Time  : O(nlogk),
        Space : O(k)

        !!!
        Under normal condition, Solution 2 (quick select) is better (O(n)).
        If "N is much larger than k", heap solution is better :
            1.quick select只是期望是O(N), 并不是真正意义上的O(N)
            2.N 远大于k，说明logk的复杂度很小，我们可以采用Nlogk复杂度的算法。
        https://www.jiuzhang.com/qa/4260/
     **/
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            pq.offer(nums[i]);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        return pq.peek();
    }

    /**
     * Quick Select:
     *
     * Time  : O(n), worst case O(n ^ 2)
     * Space : O(1)
     *
     * For another implementation for  Quick Select solution, refer to LI_005_Kth_Largest_Number
     */
    /**
        [3, 2, 1, 5, 6, 4]   k = 3
    col  0  1  2  3  4  5

    Partition 1, left=0, right=5:
         p  l           r
        [3, 2, 1, 5, 6, 4]
    col  0  1  2  3  4  5

         p     l     r
        [3, 4, 1, 5, 6, 2]
    col  0  1  2  3  4  5

         p        lr
        [3, 4, 6, 5, 1, 2]
    col  0  1  2  3  4  5

         p        r  l
        [3, 4, 6, 5, 1, 2]
    col  0  1  2  3  4  5

         p        r  l
        [5, 4, 6, 3, 1, 2]
    col  0  1  2  3  4  5

        return r (3)

        pos = 3, k - 1 = 2, pos > k - 1, right = pos - 1 = 3 - 1 = 2
        Partition 2, left=0, right=2:
         l     r
        [5, 4, 6, 3, 1, 2]
    col  0  1  2  3  4  5

         p  l  r
        [5, 4, 6, 3, 1, 2]
    col  0  1  2  3  4  5

         p  r  l
        [5, 6, 4, 3, 1, 2]
    col  0  1  2  3  4  5

         p  r  l
        [6, 5, 4, 3, 1, 2]
    col  0  1  2  3  4  5

         return r (1)

         pos = 1, k - 1 = 2, pos < k - 1, left = pos + 1 = 2
        Partition 3, left=2, right=2:

               lr
        [6, 5, 4, 3, 1, 2]
    col  0  1  2  3  4  5

        return r (2)

        pos = 2, k - 1 = 2, pos==k-1, return nums[2] = 4, 4 is the 3rd largest number in nums.

    */

    public int findKthLargest(int[] nums, int k) {
        /**
         * first set left and right value
         */
        int left = 0;
        int right = nums.length - 1;

        while (true) {
            int pos = partition(nums, left, right);

            /**
             * "pos == k - 1"
             */
            if (pos == k - 1) {
                return nums[pos];
            } else if (pos > k - 1) {//pos - 1
                right = pos - 1;
            } else if (pos < k - 1) {//pos + 1
                left = pos + 1;
            }
        }
    }

    public int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int l = left + 1;
        int r = right;
        while (l <= r) {
            /**
             * we are trying to find kth largest number,
             * therefore left part should be >= pivot,
             * right part should be <= pivot.
             */
            if (nums[l] < pivot && nums[r] > pivot) {
                swap(nums, l++, r--);
            }

            /**
             * !!!
             * it's ">=" and "<="
             */
            if (nums[l] >= pivot) l++;
            if (nums[r] <= pivot) r--;
        }

        /**
         * put pivot back, since while loop condition is "l <= r",
         * by this step, we know l and r has crossed each other.
         */
        swap(nums, left, r);
        return r;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
