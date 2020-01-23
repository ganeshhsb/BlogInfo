//package com.markata.ganesh_hs.di.dagger;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//
//class Subsets {
//    public  int getCount(int[] array) {
//        List<List<Integer>> result =  findSubsets(array);
//        Set<List<Integer>> set = new HashSet<>(result);
//        int count = 0;
//        for (List<Integer> item : set) {
//            if (item.size() == 0) {
//                continue;
//            }
//            int sum = 0;
//            for (Integer integer : item) {
//                sum += integer;
//            }
//            if (sum == 0) {
//                count++;
//            }
//        }
//        return count;
//    }
//     private List<List<Integer>> findSubsets(int[] nums) {
//        List<List<Integer>> subsets = new ArrayList<>();
//        // start by adding the empty subset
//        subsets.add(new ArrayList<>());
//        for (int currentNumber : nums) {
//            // we will take all existing subsets and insert the current number in them to create new subsets
//            int n = subsets.size();
//            for (int i = 0; i < n; i++) {
//                // create a new subset from the existing subset and insert the current element to it
//                List<Integer> set = new ArrayList<>(subsets.get(i));
//                set.add(currentNumber);
//                subsets.add(set);
//            }
//        }
//        return subsets;
//    }
//
//
//}
