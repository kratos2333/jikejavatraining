package week4_ConcurrencyProgramming.threadSafety;

import java.util.*;

public class Test {
    public static int[] singleNumber(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for(int num: nums){
            freq.put(num, freq.getOrDefault(num,0) + 1);
        }
        int[] ans = new int[2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 1) {
                ans[index++] = entry.getKey();
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(singleNumber(new int[]{1, 2, 1, 2, 3, 5})));
    }
}
