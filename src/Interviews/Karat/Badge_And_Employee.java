package Interviews.Karat;

import java.util.*;

public class Badge_And_Employee {
    /**
     * #1
     * We are working on a security system for a badged-access room in our company's building.
     * Given an ordered list of employees who used their badge to enter or exit the room, write
     * a function that returns two collections:
     *
     * 1. All employees who didn't use their badge while exiting the room
     * – they recorded an enter without a matching exit.
     * 2. All employees who didn't use their badge while entering the room  
     * – they recorded an exit without a matching enter.
     *
     * #2
     * We want to find employees who badged into our secured room unusually often.
     * We have an unordered list of names and access times over a single day. Access
     * times are given as three or four-digit numbers using 24-hour time, such as "800"
     * or "2250".
     *
     * Write a function that finds anyone who badged into the room 3 or more times in a
     * 1-hour period, and returns each time that they badged in during that period.
     * (If there are multiple 1-hour periods where this was true, just return the first one.)
     *
     * #3
     * NEW!!!
     * Given a list of people who enter and exit, find the maximal group of people who were inside
     * together at least 2 times and output the group and the times they were in together.
     *
     * All enters/exits are valid.
     *
     * ###
     * 1. 一个公司记录了门禁系统的log，检查是否有人非法进入，log是个list，list有order，每一项是一个pair，
     * 前面是string人名，后面是string "enter" 或 "exit"，一个人的出入记录形成先enter后exit的情况视为合法
     *
     * input: 上面描述的list
     * return: 两个list记录非法出入的人, 第一个list记录只有enter记录但是没有exit记录的人，第二个list记录只有exit
     *         记是但是没有enter记录的人
     * 注意：1. 同一人，enter必须出现在exit前面，如果一个人先exit后enter，说明有问题，这个人应在两个返回list都出现一次
     *      2. 每个人可以多次进出，只要有一次违反上面的要求，就需要依据情况加入返回的list
     *
     *
     * 2. 背景和第一题是一样的，只是log记录的内容有所不同，list每一项还是一个pair，前面是string人名，不会重复，后面是另一个list，
     * list里记录这个人进门的时间log（不再考虑出去）
     *
     * input: 上面描述的list
     * return:  一个list，每一项是一个pair，前项是人名，后项是个list，list的内容是对这个人的时间log做filter，
     *          如果有三个或以上连续的记录出现在一个小时范围之内，这些记录需要出现在返回的list里面，所有不overlap
     *          的这种case都要记录
     * 注意：1. 时间显示方式1350代表1点50, 1400代表两点时间是60进位，但是给的数字是正常的100才进位，算时差的时候要考虑
     *      2. 如果时间log里记录有overlap的话（[1350, 1400, 1450, 1500] 前三个记录和后三个记录都在一小时范围之内），
     *         返回前三个记录就好，面试官告诉我的是testcase不会有这种情况
     *
     * 第三道大概是同时在屋里的最大的group，而且要求group出现两次。输出group而且group在一起的那段时间.
     */

    static void getGroup(String[][] records) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> exitWithoutBadge = new HashSet<>();
        Set<String> enterWithoutBadge = new HashSet<>();

        for (String[] record : records) {
            Integer prev = map.get(record[0]);
            if (record[1].equals("enter")) {
                if (prev != null && prev == 1) {
                    exitWithoutBadge.add(record[0]);
                }
                prev = 1;
            } else {
                if (prev == null || prev == 0) {
                    enterWithoutBadge.add(record[0]);
                }
                prev = 0;
            }
            map.put(record[0], prev);
        }

        for (String person : map.keySet()) {
            if (map.get(person) > 0) {
                exitWithoutBadge.add(person);
            }
        }

        printSet("enter without badge ", enterWithoutBadge);
        printSet("exit without badge ", exitWithoutBadge);
    }

    static Map<String, List<Integer>> security(String[][] records) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (String[] record : records) {
            if (!map.containsKey(record[0])) {
                map.put(record[0], new ArrayList<>());
            }
            map.get(record[0]).add(Integer.parseInt(record[1]));

        }
        Map<String, List<Integer>> res = new HashMap<>();
        for (String person : map.keySet()) {
            if (map.get(person).size() >= 3) {
                List<Integer> cur = map.get(person);
                Collections.sort(cur);
                for (int i = 0; i < cur.size(); i++) {
                    int index = oneHour(cur, i);
                    if (index - i >= 3) {
                        List<Integer> temp = new ArrayList<>();
                        while (i < index) {
                            temp.add(cur.get(i));
                            i++;
                        }
                        res.put(person, temp);
                        break;
                    }
                }
            }
        }
        printMap("secure ", res);
        return res;
    }

    static int oneHour(List<Integer> list, int startIndex) {
        int endVal = list.get(startIndex) + 100;
        int endPos = startIndex;
        while (endPos < list.size()) {
            if (list.get(endPos) <= endVal) {
                endPos++;
            } else {
                break;
            }
        }
        return endPos;
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for (String i : set) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for (String i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for (Integer i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for (String ss : map.keySet()) {
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[]) {
        String[][] records = new String[][]{
                {"Martha", "exit"},
                {"Paul", "enter"},
                {"Martha", "enter"},
                {"Martha", "exit"},
                {"Jennifer", "enter"},
                {"Paul", "enter"},
                {"Curtis", "enter"},
                {"Paul", "exit"},
                {"Martha", "enter"},
                {"Martha", "exit"},
                {"Jennifer", "exit"},
        };

        String[][] records2 = new String[][]{
                {"Paul", "1355"},
                {"Jennifer", "1910"},
                {"John", "830"},
                {"Paul", "1315"},
                {"John", "835"},
                {"Paul", "1405"},
                {"Paul", "1630"},

                {"John", "855"},

                {"John", "915"},

                {"John", "930"},

                {"Jennifer", "1335"},

                {"Jennifer", "730"},

                {"John", "1630"},

        };
        getGroup(records);
        security(records2);
    }
}

