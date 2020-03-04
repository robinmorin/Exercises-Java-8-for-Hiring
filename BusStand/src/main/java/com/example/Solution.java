package com.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        LocalDateTime ini = LocalDateTime.now();
        int k = 2;
        List<Integer> p = Arrays.asList(1,4,4,3,1,2,6);
        List<Integer> q = Arrays.asList(1,2,3,4,5,6,7);
//        int k = 3;
//        List<Integer> p = Arrays.asList(2,5,3);
//        List<Integer> q = Arrays.asList(1,5);
        System.out.println("Time construct: "+ String.valueOf(Duration.between(ini, LocalDateTime.now()).toMillis())+"mlsegs");
        LocalDateTime meio = LocalDateTime.now();
        List<Integer> result = kthPerson(k,p, q);
        System.out.println("Time process: "+ String.valueOf(Duration.between(meio, LocalDateTime.now()).toMillis())+"mlsegs");
        System.out.println(result);

    }

    public static List<Integer> kthPerson(int k, List<Integer> p, List<Integer> q) {
        if (k < 0 || p.isEmpty() || p.size() > 10e5 || q.isEmpty() || q.size() > 10e5) return new ArrayList<>();
        List<Integer> listReturn = new ArrayList<>();
        int lastPerson = 0;
        int fillCapacity = 0;
        for(int i =0; i < q.size(); i++){
            fillCapacity = 0;
            Integer busTime = q.get(i);
            if(p.stream().filter(patience -> busTime.compareTo(patience)<=0).count()>=k)
                for(lastPerson = 0; lastPerson < p.size(); lastPerson++)
                    if(busTime.compareTo(p.get(lastPerson))<=0 && (k == ++fillCapacity)) break;
            listReturn.add(fillCapacity < k ? 0: ++lastPerson);
        }
        return listReturn;
    }

}


