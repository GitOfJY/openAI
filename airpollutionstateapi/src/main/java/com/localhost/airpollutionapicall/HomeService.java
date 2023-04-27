package com.localhost.airpollutionapicall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HomeService {
	
	public static String callApi(String city) throws UnsupportedEncodingException, IOException {
		
	    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst"); /*URL*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=인증키입력"); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml 또는 json*/
	    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	    urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")); /*시도 이름*/
	    urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8")); /*요청 데이터기간*/
	    
	    URL url = new URL(urlBuilder.toString());
	    
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    
	    BufferedReader rd;
	    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	        sb.append(line);
	    }
	    rd.close();
	    conn.disconnect();
	    
	    return sb.toString();
		
	}
	

}
