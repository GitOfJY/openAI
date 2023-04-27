package chatgptwithairpollution;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class callApi {
	
	static JSONArray reqArry = new JSONArray();
	final static String requestURL = "https://api.openai.com/v1/chat/completions";
	static HttpPost postRequest = new HttpPost(requestURL);
	static Scanner sc = new Scanner(System.in);
		
	// api 호출
	public void callOpenAi(HttpClient client, String newFilePath, int input, BufferedWriter bf) throws IOException {
		
		fileMethod fm = new fileMethod();    		
		String text;
		String answer;
		
		// 파일 대화
		if (input == 1) {	
            String readFilePath = "C:\\apiTestTxt\\";    
            int file_list_length = fm.file_list(readFilePath);
            
            System.out.println("해당하는 파일 번호를 입력하세요.");
            int fileNum = sc.nextInt();
            
            while (fileNum > file_list_length ||  fileNum < 0) {
                 System.out.println("( 범위에 벗어나는 번호 입니다. )\n\n");
                 System.out.println("해당하는 파일 번호를 입력하세요.");
                 fileNum = sc.nextInt();
            }
            
            String fileName = fm.find_file_name(readFilePath, fileNum);

            text = fm.read_file(readFilePath, fileName);
            System.out.println("사용자 : "+text);
            bf.write("\n사용자 : "+text+"\n");
            
            if (text.equals("")) {
            	System.out.println("파일의 내용이 비어있습니다.");
            	bf.write("파일의 내용이 비어있습니다.\n");
            } else {
            	answer = getApiAnswer(client, text);
            	System.out.println("api : "+answer);
            	bf.write("\napi : "+answer+"\n");
            }
		
        // 사용자 대화    
		} else if (input == 2) {
			
			int cnt = 0;
			
	        while (true) {
	        	
				System.out.println("사용자 : ");
				text = sc.nextLine();
				
				if (text.toUpperCase().equals("N")) {
					System.out.println();
					System.out.println("\'N(n)\'을 입력하셨습니다.");
					System.out.println("대화를 종료합니다.");
					break;
				} else if (text.equals("") & cnt!=0) {
					bf.write("\n사용자 : "+text+"\n");
					bf.write("\n내용을 입력하세요.\n");
					System.out.println("내용을 입력하세요.");
				} else {
					bf.write("\n사용자 : "+text+"\n");
					answer = getApiAnswer(client, text);
					System.out.println("api : "+answer);
					bf.write("\napi : "+answer+"\n");
				}
				
				cnt ++;
				
	        }
        
        } //while
        		
	} // callOpenAi
	
	
	// api 호출 + 응답 받기
	public String getApiAnswer(HttpClient client, String text) {
		
		JSONObject inputMessage = new JSONObject();
		JSONObject data = new JSONObject();
		
		JSONParser parser = new JSONParser();
        JSONObject jObject;            
        JSONArray jArray;   
        JSONObject jobj;
        String apiAnswer;
        		
        try {
    		
    		inputMessage.put("role", "user");
	        inputMessage.put("content", text);
	        reqArry.add(inputMessage);
                            
            data.put("model", "gpt-3.5-turbo");
            data.put("messages", reqArry);
            data.put("max_tokens", 4000);
            data.put("temperature", 0.0); 
            
            // httpPost
            postRequest = new HttpPost(requestURL);
        	postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.addHeader("Authorization", "Bearer 인증키");		
            	
            postRequest.setEntity(new StringEntity(data.toString(), "UTF-8"));
            HttpResponse response = client.execute(postRequest);   
            
            if (response.getStatusLine().getStatusCode() == 200) {
                
            	ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                
                jObject = (JSONObject) parser.parse(body);
                jArray = (JSONArray) jObject.get("choices");		            
                jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 	
                apiAnswer = (String) jobj.get("content");
            
                return apiAnswer;   
                
            } else {
            	
            	String body = EntityUtils.toString(response.getEntity());
            	System.out.println("+++++++++++++bad requeset body 확인+++++++++++++");
            	System.out.println(response.getStatusLine().getStatusCode());
            	System.out.println(body);
            	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");

            	// 토큰 초과로 예외 발생했을 때, 초기화
            	reqArry.removeAll(reqArry);
            	System.out.println("토큰 소진으로 대화내용 초기화");
            	System.out.println("다시 입력해 주세요.");
            	
            } // if-else        	
        	
		} catch (Exception e) {
			System.out.println("예외발생 - checkResponsebody");
		}
        
        return "토큰 소진으로 대화내용 초기화 (다시 입력해 주세요.)";
		
	} // getApiAnswer()

} // callApi
