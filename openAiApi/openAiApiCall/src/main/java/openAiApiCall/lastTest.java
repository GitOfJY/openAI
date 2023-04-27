package openAiApiCall;

import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class lastTest {
	
	public static void main(String[] args) throws Exception {		
		
		lastTest lt = new lastTest();
		
		// 1.
		// lt.lastChatTest();
		

		// 2.
		// httpClientTest
		// String requestURL = "https://api.openai.com/v1/chat/completions"; 	
    	// HttpClient client = HttpClientBuilder.create().build();		
    	// HttpPost postRequest = new HttpPost(requestURL);
		
    	// postRequest.setHeader("Connection", "keep-alive");
        // postRequest.setHeader("Content-Type", "application/json");
        // postRequest.addHeader("Authorization", "Bearer 인증키");
		
        // lt.httpClientTest(client, postRequest);
        
        
        // 3.
        // lt.tockenTest(client, postRequest);
        
        // 4. 
		lt.breakTest();
		
	
	} // main
	
	// HttpClient + gpt-3.5-turbo + user, user만 저장, 각각 json 5개로 만들고 cnt=5이면 초기화
	public void lastChatTest() throws Exception { 
		
		try {
	
			String requestURL = "https://api.openai.com/v1/chat/completions"; 	
	    	HttpClient client = HttpClientBuilder.create().build();		
	    	HttpPost postRequest = new HttpPost(requestURL);
			
	    	postRequest.setHeader("Connection", "keep-alive");
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.addHeader("Authorization", "Bearer 인증키");
	        
	        JSONArray reqArry = new JSONArray();
	        	        	        
	        JSONObject inputMessage1 = new JSONObject();
	        JSONObject inputMessage2 = new JSONObject();
	        JSONObject inputMessage3 = new JSONObject();
	        JSONObject inputMessage4 = new JSONObject();
	        JSONObject inputMessage5 = new JSONObject();
	        
	        JSONObject data = new JSONObject();
	        
	        String input = "Y";
	        String text = "";
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        String apiAnswer;
	        	        
	        int cnt = 0;
	        
	        while (!input.equals("N")) {
	        	
	        	cnt ++;
		        
		        System.out.println("\n\n===========================================");
		        System.out.println(cnt);
		        System.out.println("---------------------------------------------------");
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("질문을 입력하세요.");
				text = sc.nextLine();
				
				if (cnt==1) {
			        inputMessage1.put("role", "user");
			        inputMessage1.put("content", text);
			        reqArry.add(inputMessage1);
				} else if (cnt==2) {
			        inputMessage2.put("role", "user");
			        inputMessage2.put("content", text);
			        reqArry.add(inputMessage2);					
				} else if (cnt==3) {
			        inputMessage3.put("role", "user");
			        inputMessage3.put("content", text);
			        reqArry.add(inputMessage3);						
				} else if (cnt==4) {
			        inputMessage4.put("role", "user");
			        inputMessage4.put("content", text);
			        reqArry.add(inputMessage4);						
				} else {
			        inputMessage5.put("role", "user");
			        inputMessage5.put("content", text);
			        reqArry.add(inputMessage5);											
				}
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 0.0); 
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
	        
		        // Response 출력
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            jObject = (JSONObject) parser.parse(body);
		            jArray = (JSONArray) jObject.get("choices");		            
		            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 	
		            apiAnswer = (String) jobj.get("content");
	            
		            System.out.println(apiAnswer);
		            
			        System.out.println("\n\n===========================================");
			        System.out.println("네 번째 reqArry, assisAnswer add : " + reqArry.toString());
			        System.out.println("---------------------------------------------------");
			        
			        if (cnt==5) {
			        	reqArry.removeAll(reqArry);
			        	System.out.println("cnt=5 일때 reqArry 초기화 : " + reqArry.toString());
			        	cnt = 0;
			        }
		            
	            } else {
	            	String body = EntityUtils.toString(response.getEntity());
	            	System.out.println("+++++++++++++bad requeset body 확인+++++++++++++");
	            	System.out.println(response.getStatusLine().getStatusCode());
	            	System.out.println(body);
	            	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	            } 
	        }
		} catch (Exception e) {
				System.out.println("예외발생");
		}
	} // lastChatTest
	

	// httpClient를 main에서 열고,,, 중간 중간 메서드를 호출해서 다 사용 후 다시 main에서 close가 가능 여부 확인
	public void httpClientTest(HttpClient client, HttpPost postRequest) throws Exception { 
		
		try {
	        
	        JSONArray reqArry = new JSONArray();
	        	        	        
	        JSONObject inputMessage1 = new JSONObject();
	        JSONObject inputMessage2 = new JSONObject();
	        JSONObject inputMessage3 = new JSONObject();
	        JSONObject inputMessage4 = new JSONObject();
	        JSONObject inputMessage5 = new JSONObject();
	        
	        JSONObject data = new JSONObject();
	        
	        String input = "Y";
	        String text = "";
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        String apiAnswer;
	        	        
	        int cnt = 0;
	        
	        while (!input.equals("N")) {
	        	
	        	cnt ++;
		        
		        System.out.println("\n\n===========================================");
		        System.out.println(cnt);
		        System.out.println("---------------------------------------------------");
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("질문을 입력하세요.");
				text = sc.nextLine();
				
				if (cnt==1) {
			        inputMessage1.put("role", "user");
			        inputMessage1.put("content", text);
			        reqArry.add(inputMessage1);
				} else if (cnt==2) {
			        inputMessage2.put("role", "user");
			        inputMessage2.put("content", text);
			        reqArry.add(inputMessage2);					
				} else if (cnt==3) {
			        inputMessage3.put("role", "user");
			        inputMessage3.put("content", text);
			        reqArry.add(inputMessage3);						
				} else if (cnt==4) {
			        inputMessage4.put("role", "user");
			        inputMessage4.put("content", text);
			        reqArry.add(inputMessage4);						
				} else {
			        inputMessage5.put("role", "user");
			        inputMessage5.put("content", text);
			        reqArry.add(inputMessage5);											
				}
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 0.0); 
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
	        
		        // Response 출력
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            jObject = (JSONObject) parser.parse(body);
		            jArray = (JSONArray) jObject.get("choices");		            
		            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 	
		            apiAnswer = (String) jobj.get("content");
	            
		            System.out.println(apiAnswer);
		            
			        System.out.println("\n\n===========================================");
			        System.out.println("네 번째 reqArry, assisAnswer add : " + reqArry.toString());
			        System.out.println("---------------------------------------------------");
		            
	            } else {
	            	String body = EntityUtils.toString(response.getEntity());
	            	System.out.println("+++++++++++++bad requeset body 확인+++++++++++++");
	            	System.out.println(response.getStatusLine().getStatusCode());
	            	System.out.println(body);
	            	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	            } 
	        }
		} catch (Exception e) {
				System.out.println("예외발생");
		}
	} // lastChatTest
	

	// 토큰이 소진되었을 때 대화를 계속 진행할 수 있도록 수정
	public void tockenTest(HttpClient client, HttpPost postRequest) throws Exception { 
		
		try {
	        
	        JSONArray reqArry = new JSONArray();
	        	        	        
	        JSONObject inputMessage1 = new JSONObject();
	        JSONObject inputMessage2 = new JSONObject();
	        JSONObject inputMessage3 = new JSONObject();
	        JSONObject inputMessage4 = new JSONObject();
	        JSONObject inputMessage5 = new JSONObject();
	        
	        JSONObject data = new JSONObject();
	        
	        String input = "Y";
	        String text = "";
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        String apiAnswer;
	        	        
	        int cnt = 0;
	        
	        while (true) {
	        	
	        	cnt ++;
		        
		        System.out.println("\n\n===========================================");
		        System.out.println(cnt);
		        System.out.println("---------------------------------------------------");
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("질문을 입력하세요.");
				text = sc.nextLine();
				
				if (text.equals("N")) {
					break;
				}
				
				if (cnt==1) {
			        inputMessage1.put("role", "user");
			        inputMessage1.put("content", text);
			        reqArry.add(inputMessage1);
				} else if (cnt==2) {
			        inputMessage2.put("role", "user");
			        inputMessage2.put("content", text);
			        reqArry.add(inputMessage2);					
				} else if (cnt==3) {
			        inputMessage3.put("role", "user");
			        inputMessage3.put("content", text);
			        reqArry.add(inputMessage3);						
				} else if (cnt==4) {
			        inputMessage4.put("role", "user");
			        inputMessage4.put("content", text);
			        reqArry.add(inputMessage4);						
				} else {
			        inputMessage5.put("role", "user");
			        inputMessage5.put("content", text);
			        reqArry.add(inputMessage5);											
				}
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 0.0); 
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
	        
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            jObject = (JSONObject) parser.parse(body);
		            jArray = (JSONArray) jObject.get("choices");		            
		            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 	
		            apiAnswer = (String) jobj.get("content");
	            
		            System.out.println(apiAnswer);
		            
	            } else {	            	
	            	// 토큰 초과로 예외 발생했을 때, 초기화
	            	reqArry.removeAll(reqArry);
		        	System.out.println("토큰 소진으로 reqArry 초기화");
		        	System.out.println("다시 입력해 주세요.");
		        	cnt = 0;
	            } 
	        }
	        
	        System.out.println("시스템이 종료되었습니다.");
	        
		} catch (Exception e) {
				System.out.println("예외발생");
		}
	} // tockenTest
	
	
	// while (true) { try { if-else } catch {} } 구조에서 if - break하면 try/while도 같이 빠져나가고 끝?
	public void breakTest() {
		String text;
		System.out.println("while 시작");
		while (true) {
			try {
				System.out.println("try 시작");
				Scanner sc = new Scanner(System.in);
				System.out.println("질문을 입력하세요.");
				text = sc.nextLine();
				if (text.equals("N")) {
					System.out.println("+++++++++++++++++++");
					System.out.println("텍스트 N 입력");
					System.out.println("프로그램 종료합니다.");
					System.out.println("+++++++++++++++++++");					
					break;
				}
				System.out.println("try 끝");
			} catch (Exception e) {
				System.out.println("catch 부분");
			}
			System.out.println("try-catch문 끝");
		}
		System.out.println("while 끝");		
	} // breakTest()
	
	
	public void fileTest() {
		
	}
	
}
