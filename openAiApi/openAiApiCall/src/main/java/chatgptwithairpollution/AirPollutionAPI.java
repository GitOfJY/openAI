package chatgptwithairpollution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AirPollutionAPI {
	
    public static void main(String[] args) throws IOException {
    	
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRDyrg"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=인증키"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("inqBginDt","UTF-8") + "=" + URLEncoder.encode("20230101", "UTF-8")); /*조회시작일자*/
        urlBuilder.append("&" + URLEncoder.encode("inqEndDt","UTF-8") + "=" + URLEncoder.encode("20230103", "UTF-8")); /*조회종료일자*/
        urlBuilder.append("&" + URLEncoder.encode("msrstnName","UTF-8") + "=" + URLEncoder.encode("강남구", "UTF-8")); /*측정소명*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
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
        System.out.println(sb.toString());
    }

}
