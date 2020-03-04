package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Predicate;

public class Solution {

    private static final String URL_HACKER_RANK = "https://jsonmock.hackerrank.com/api/transactions/search";
    private static HttpURLConnection httpClient;
    private static URL httpGetUrl;

    public static void main(String[] args) {
        int userId = 2;
        int locationId = 8;
        int netStart = 5;
        int netEnd = 50;
        int result = getExpenditure(userId,locationId,netStart,netEnd);
        System.out.println(result);
    }

    public static int getExpenditure(int userId, int locationId, int netStart, int netEnd) {
        JSONObject response;
        BigDecimal[] total_amount = new BigDecimal[]{BigDecimal.ZERO};
        try{
            response = callHttpGet(String.valueOf(userId));
            Long page = (Long)response.get("page");
            Long totalPages = (Long)response.get("total_pages");
            for (;true;) {
                JSONArray data = (JSONArray) response.get("data");
                data.stream().filter(filterLocation(Long.valueOf(locationId)).and(filtersIp(netStart, netEnd)))
                        .forEach(element -> {
                            String amount = String.valueOf(((JSONObject) element).get("amount")).replaceAll("[$,]", "");
                            total_amount[0] = total_amount[0].add(BigDecimal.valueOf(Double.valueOf(amount))).setScale(2);
                        });
                if(page.compareTo(totalPages) == 0) break;
                response = callHttpGet(String.valueOf(userId),String.valueOf(++page));
            }
        } catch (IOException e) {
            System.out.println("Error: connecting to URL Address is fail - ".concat(e.getMessage()));
        }catch (ParseException e) {
            System.out.println("Error: Parsing response of CallHttpGet - ".concat(e.getMessage()));
        }
        return total_amount[0].setScale(0, RoundingMode.HALF_UP).intValue();
    }

    private static JSONObject callHttpGet(String...params) throws IOException, ParseException {
        StringBuilder uri = new StringBuilder(URL_HACKER_RANK);
        if (params.length ==1) uri.append("?userId=").append(params[0]);
        if (params.length ==2) uri.append("?userId=").append(params[0]).append("&page=").append(params[1]);
        httpGetUrl = new URL(uri.toString());
        httpClient = (HttpURLConnection) httpGetUrl.openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("Accept", "application/json");
        httpClient.setDoOutput(false);
        DataInputStream dataInputStream = new DataInputStream(httpClient.getInputStream());
        byte[] allbytes = new byte[dataInputStream.available()];
        dataInputStream.readFully(allbytes,0,dataInputStream.available());
        dataInputStream.close();
        String responseAsString = new String(allbytes);
        return (JSONObject) new JSONParser().parse(responseAsString);
    }

    private static Predicate<JSONObject> filterLocation(Long locationId){
        return p -> locationId.equals(((JSONObject)(p.get("location"))).get("id"));
    }

    private static Predicate<JSONObject> filtersIp(Integer netStart, Integer netEnd){
        return p -> {
            Integer firstByte = Integer.valueOf(String.valueOf(p.get("ip")).split("\\.")[0]);
            return firstByte.compareTo(netStart) >= 0 && firstByte.compareTo(netEnd) <=0;
        };
    }
}


