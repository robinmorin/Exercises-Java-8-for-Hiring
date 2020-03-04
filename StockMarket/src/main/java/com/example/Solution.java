package com.example;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Solution {

    public static void main(String[] args){
        List<Integer> stockData = Arrays.asList(
                89214,
                26671,
                75144,
                32445,
                13656,
                66289,
                21951,
                10265,
                59857,
                59133,
                63227,
                86121,
                37411,
                54628,
                25859,
                43510,
                63756,
                54763,
                30852,
                53243,
                76238,
                96885,
                33074,
                17745,
                81814,
                43436,
                79172,
                92819,
                30001,
                68442,
                54021,
                35566,
                95113,
                29164,
                84362,
                25120,
                11804,
                6313,
                51736,
                71661,
                81797,
                14962,
                57781,
                35560,
                85941,
                99991,
                95421,
                66048,
                54754,
                26272,
                35642,
                47343,
                39508,
                85068,
                65087,
                21321,
                28503,
                60611,
                30491,
                58503,
                29052,
                84512,
                94069,
                40516,
                13675,
                78430,
                65635,
                25479,
                1094,
                17370,
                13491,
                99243,
                48683,
                71271,
                34802,
                34624,
                87613,
                46574,
                671,
                42366,
                89197,
                36313,
                89708,
                28704,
                21380,
                54795,
                66376,
                49882,
                15405,
                96867,
                24737,
                60808,
                81378,
                35157,
                1324,
                11404,
                29938,
                66958,
                53234,
                47384
        );
        List<Integer> queries = Arrays.asList(
                80,
                24,
                26,
                62,
                46,
                79,
                85,
                59,
                52,
                8,
                76,
                48,
                72,
                84,
                3,
                3,
                30,
                30,
                36,
                86,
                96,
                72,
                93,
                25,
                28,
                68,
                81,
                18,
                78,
                14,
                1,
                57,
                90,
                26,
                18,
                87,
                56,
                55,
                97,
                59,
                62,
                73,
                58,
                85,
                8,
                60,
                87,
                89,
                89,
                22
        );

        List<Integer> result = predictAnswer(stockData,queries);
        System.out.println(result.toString());

    }

    public static List<Integer> predictAnswer(List<Integer> stockData , List<Integer> queries) {
        //Validation of List length
        if(queries == null || queries.size() == 0 || stockData == null || stockData.size() == 0 )  return Collections.singletonList(-1);
        List<Integer> result = new ArrayList<>();
        if(queries.size() <= 10e5 && stockData.size() <= 10e5){
            Integer[] tmpArray = new Integer[stockData.size()];
            tmpArray = stockData.toArray(tmpArray);
            final Integer[] stockDataArray = tmpArray;
            queries.stream().filter(query -> (query >= 1 && query <= 10e9)).forEach( q -> {
                int priceRefDay = stockDataArray[q-1];
                // Evaluating min value to the left direction of day
                int lenLeft = -1;
                for(int i=q-1; i >= 0; i--){
                    if(stockDataArray[i].compareTo(priceRefDay) == -1){
                        lenLeft = i+1;
                        break;
                    }
                }
                // Evaluating min value to the right direction of day
                int lenRight = -1;
                for(int j=q; j < stockDataArray.length ;j++){
                    if(stockDataArray[j].compareTo(priceRefDay) == -1){
                        lenRight = j+1;
                        break;
                    }
                }
                // Calculating result
                if(lenLeft == -1 && lenRight == -1) result.add(-1);
                else if(lenLeft == -1) result.add(lenRight);
                else if(lenRight == -1) result.add(lenLeft);
                else if((q-lenLeft) == (lenRight-q)) result.add(lenLeft);
                else result.add((q-lenLeft) > (lenRight-q) ? lenRight :lenLeft);
            });
        }
        return result;
    }
}


