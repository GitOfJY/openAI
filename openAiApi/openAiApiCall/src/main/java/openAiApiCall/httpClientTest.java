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

public class httpClientTest {
	
	public static void main(String[] args) throws Exception {		
		
		String input = "Y";
		
		while (!input.equals("N")) {
			//chatDavinci(input);
			//chatTurbo(input);
			//jsonDuplicationTest(input);
			lastChatTest(input);
		}
		
	}
		
		
	// HttpClient + text-davinci-003
	public static String chatDavinci(String text) throws Exception {
	    	
	    try {
	    	
	    	String requestURL = "https://api.openai.com/v1/completions"; 	
	    	// HttpClient 생성
	    	HttpClient client = HttpClientBuilder.create().build();		
	    	
	    	// POST 메서드 URL 생성
	    	HttpPost postRequest = new HttpPost(requestURL);
	    	
	    	postRequest.setHeader("Accept", "application/json");
	    	postRequest.setHeader("Connection", "keep-alive");
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.addHeader("Authorization", "Bearer 인증키");
	        
	        String input = "Y";
	        
	        while (!input.equals("N")) {
	        
		        Scanner sc = new Scanner(System.in);
				System.out.println("입력하세요.");
				text = sc.nextLine();
		        
		        // JSON 메세지
		        JSONObject data = new JSONObject();
	            data.put("model", "text-davinci-003");
	            data.put("prompt", text);
	            data.put("max_tokens", 4000);
	            data.put("temperature", 1.0);
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
		    	
		        // Response 출력
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            JSONParser parser = new JSONParser();
		            JSONObject jObject = (JSONObject) parser.parse(body);
		            JSONArray jArray = (JSONArray) jObject.get("choices");
		            JSONObject jobj = (JSONObject)jArray.get(0);
		            String apiAnswer = (String)jobj.get("text");
		            
		            System.out.println(apiAnswer);
		           
	            } else {
		            System.out.println("response is error : " + response.getStatusLine().getStatusCode());
	            } //else
	        
	        } // while 	
	    } //try
	    		
			catch (Exception e) {
				// TODO: handle exception				
		} //try 
		return "";
	    
	}
	
	
	
	// HttpClient + gpt-3.5-turbo
	public static String chatTurbo(String input) throws Exception { 
		
		try {
	
			String requestURL = "https://api.openai.com/v1/chat/completions"; 	
	    	HttpClient client = HttpClientBuilder.create().build();		
	    	HttpPost postRequest = new HttpPost(requestURL);
			
	    	// postRequest.setHeader("Accept", "application/json");
	    	postRequest.setHeader("Connection", "keep-alive");
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.addHeader("Authorization", "Bearer 인증키");
	        
	        JSONArray reqArry = new JSONArray();
			
	        JSONObject answer = new JSONObject();
	        JSONObject inputMessage = new JSONObject();
	        JSONObject assisAnswer = new JSONObject();    
	        JSONObject data = new JSONObject();
	        
	        String text = "";
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        String apiAnswer;
	        
	        	        
	        
	        while (!input.equals("N")) {
	        	
	        	answer.put("role", "system");
		        answer.put("content", "You are a helpful assistant.");
		        reqArry.add(answer);
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("입력하세요.");
				text = sc.nextLine();
		        
		        inputMessage.put("role", "user");
		        inputMessage.put("content", text);
		        reqArry.add(inputMessage);
		
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 1.0); 
		        System.out.println("----------------------------------");
		        System.out.println("reqArry 출력 : \n");
		        System.out.println(reqArry.toString());
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
	        
		        // Response 출력
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            jObject = (JSONObject) parser.parse(body);
		            jArray = (JSONArray) jObject.get("choices");		            
		            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 
		            System.out.println("----------------------------------------");
		            System.out.println("Response 출력 : \n");
		            System.out.println(jobj.toString());
	
		            apiAnswer = (String) jobj.get("content");
	            
		            System.out.println(apiAnswer);
		            
		            assisAnswer.put("role", "assistant");
		            assisAnswer.put("content", apiAnswer);
		            reqArry.add(assisAnswer);
		            
		            System.out.println(reqArry.toString());
		            
//		            reqArry.removeAll(reqArry);
//		            System.out.println(reqArry.toString());
		            

	            } else {
	            	
	            	// responsebody
	            	String body = EntityUtils.toString(response.getEntity());
	            	System.out.println("+++++++++++++bad requeset body 확인+++++++++++++");
	            	System.out.println(response.getStatusLine().getStatusCode());
	            	System.out.println(body);
	            	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");

//                  +++++++++++++bad requeset body 확인+++++++++++++	            	
//	            	400
//	            	{
//	            	  "error": {
//	            	    "message": "This model's maximum context length is 4097 tokens. 
//	            	                However, you requested 4242 tokens (242 in the messages, 4000 in the completion). 
//	                            	Please reduce the length of the messages or completion.",
//	            	    "type": "invalid_request_error",
//	            	    "param": "messages",
//	            	    "code": "context_length_exceeded"
//	            	  }
//	            	}
//                  ++++++++++++++++++++++++++++++++++++++++++++++++++
	        
	            } //else 
	        
	        } //while
	            
		} catch (Exception e) {
				System.out.println("예외발생");
		} //trycath
		
		return "";
	} // chatTurbo
	
	
	// HttpClient + gpt-3.5-turbo + duplication test
	public static String jsonDuplicationTest(String input) throws Exception { 
		
		try {
	
			String requestURL = "https://api.openai.com/v1/chat/completions"; 	
	    	HttpClient client = HttpClientBuilder.create().build();		
	    	HttpPost postRequest = new HttpPost(requestURL);
			
	    	postRequest.setHeader("Connection", "keep-alive");
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.addHeader("Authorization", "Bearer 인증키");
	        
	        JSONArray reqArry = new JSONArray();
	        JSONObject answer = new JSONObject();
	        JSONObject inputMessage = new JSONObject();
	        JSONObject assisAnswer = new JSONObject();    
	        JSONObject data = new JSONObject();
	        
	        String text = "";
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        String apiAnswer;
	        	        
	        int cnt = 0;
	        
	        while (!input.equals("N")) {
	        	
	        	cnt ++;
	        	
	        	answer.put("role", "system");
		        answer.put("content", "You are a helpful assistant.");
		        reqArry.add(answer);
		        
		        System.out.println("\n\n===========================================");
		        System.out.println(cnt);
		        System.out.println("첫 번째 reqArry, answer add : " + reqArry.toString());
		        System.out.println("---------------------------------------------------");
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("입력하세요.");
				text = sc.nextLine();
		        
		        inputMessage.put("role", "user");
		        inputMessage.put("content", text);
		        reqArry.add(inputMessage);
		        
		        System.out.println("\n\n===========================================");
		        System.out.println("두 번째 reqArry, inputMessage add : " + reqArry.toString());
		        System.out.println("---------------------------------------------------");
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 1.0); 
		        
		        System.out.println("\n\n===========================================");
		        System.out.println("data put 하고 난 뒤 reqArry : " + reqArry.toString());
		        System.out.println("---------------------------------------------------");
		        
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
		            
		            assisAnswer.put("role", "assistant");
		            assisAnswer.put("content", apiAnswer);
		            reqArry.add(assisAnswer);
		            
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
		return "";
	} // jsonDuplicationTest	
	
	
	// HttpClient + gpt-3.5-turbo + reqArry 초기화하고 system content에 답변 이어 붙이기
	public static void chatTurboV2(String input) throws Exception {
		
		try {
			
			String requestURL = "https://api.openai.com/v1/chat/completions"; 	
	    	HttpClient client = HttpClientBuilder.create().build();		
	    	HttpPost postRequest = new HttpPost(requestURL);
			
	    	postRequest.setHeader("Connection", "keep-alive");
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.addHeader("Authorization", "Bearer 인증키");
	        
	        JSONArray reqArry = new JSONArray();
	        JSONObject answer = new JSONObject();
	        JSONObject inputMessage = new JSONObject();
	        JSONObject assisAnswer = new JSONObject();
	        JSONObject data = new JSONObject();
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        
	        String apiAnswer;
	        String rmAnswer = "";
	        int cnt = 0;
	        	        
	        answer.put("role", "system");
	        answer.put("content", "You are a helpful assistant.");
	        reqArry.add(answer);
	        
	        while (!input.equals("N")) {
	        	
	        	// 루프 질문 횟수 저장
	        	cnt ++;
	        	System.out.println(cnt);
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("입력하세요.");
				input = sc.nextLine();
		        
		        inputMessage.put("role", "user");
		        inputMessage.put("content", input);
		        reqArry.add(inputMessage);
		
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 1.0); 
		        System.out.println("----------------------------------");
		        System.out.println("reqArry 출력 : \n");
		        System.out.println(reqArry.toString());
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
	        
		        // Response 출력
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            jObject = (JSONObject) parser.parse(body);
		            jArray = (JSONArray) jObject.get("choices");		            
		            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 
		            System.out.println("----------------------------------------");
		            System.out.println("Response 출력 : \n");
		            System.out.println(jobj.toString());
	
		            apiAnswer = (String) jobj.get("content");
	            
		            System.out.println(apiAnswer);
		            
		            // 정상 답변을 받았다면, 답변은 system에 저장하고 reqArray는 초기화
			        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
			        System.out.println("reqArry 출력 : ");
			        System.out.println(reqArry.toString());
			        // [{"role":"system","content":"You are a helpful assistant."},{"role":"user","content":"recommend movies"}]
			        
			        System.out.println(reqArry.get(0).toString());
			        // {"role":"system","content":"You are a helpful assistant."}
			        
			        System.out.println("rmAnswer 초기화 보기===========================================");
			        System.out.println("rmAnswer" + rmAnswer);
			        System.out.println("apiAnswer : " + apiAnswer);
			        rmAnswer = apiAnswer + " " + rmAnswer;
			        System.out.println("변경 후 rmAnswer : " + rmAnswer);
			        System.out.println("=============================================================");
			        
			        reqArry.removeAll(reqArry);
			        System.out.println(reqArry.toString());
			        
			        answer.put("role", "system");
			        answer.put("content", rmAnswer);
			        reqArry.add(answer);
			        
		            assisAnswer.put("role", "assistant");
		            assisAnswer.put("content", apiAnswer);
		            reqArry.add(assisAnswer);
			        
			        System.out.println("++++++++++++++++중요++++++++++++++++++");
			        System.out.println(rmAnswer);
			        // 누적 저장 안 됨, 어디서 다시 초기화 되는가본데...

	            } else {
	            	
	            	// error > responsebody
	            	System.out.println("+++++++++++++bad requeset body 확인+++++++++++++");
	            	System.out.println(response.getStatusLine().getStatusCode());
	            	System.out.println(EntityUtils.toString(response.getEntity()));
	            	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	        
	            } // if-else 
	        
	        } //while
	            
		} catch (Exception e) {
				System.out.println("예외발생");
		} //trycath
		
	} //chatTurboV2
	
	
	// HttpClient + gpt-3.5-turbo + reqArry 초기화하고 system JSONObject만 누적 넣기
	public static void chatTurboV3(String input) throws Exception {
		
		try {
			
			String requestURL = "https://api.openai.com/v1/chat/completions"; 	
	    	HttpClient client = HttpClientBuilder.create().build();		
	    	HttpPost postRequest = new HttpPost(requestURL);
			
	    	postRequest.setHeader("Connection", "keep-alive");
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.addHeader("Authorization", "Bearer 인증키");
	        
	        JSONArray reqArry = new JSONArray();
	        JSONObject answer = new JSONObject();
	        JSONObject inputMessage = new JSONObject();
	        JSONObject assisAnswer = new JSONObject();
	        JSONObject data = new JSONObject();
	        
	        JSONParser parser = new JSONParser();
	        JSONObject jObject;            
	        JSONArray jArray;   
	        JSONObject jobj;  
	        
	        String apiAnswer;
	        int cnt = 0;
	        	        
	        answer.put("role", "system");
	        answer.put("content", "You are a helpful assistant.");
	        reqArry.add(answer);
	        
	        while (!input.equals("N")) {
	        	
	        	// 루프 질문 횟수 저장
	        	cnt ++;
	        	System.out.println(cnt);
		        
		        Scanner sc = new Scanner(System.in);
				System.out.println("입력하세요.");
				input = sc.nextLine();
		        
		        inputMessage.put("role", "user");
		        inputMessage.put("content", input);
		        reqArry.add(inputMessage);
		
		                        
		        data.put("model", "gpt-3.5-turbo");
		        data.put("messages", reqArry);
		        data.put("max_tokens", 4000);
		        data.put("temperature", 1.0); 
		        
		        System.out.println("----------------------------------");
		        System.out.println("reqArry 출력 : \n");
		        System.out.println(reqArry.toString());
		        
		        postRequest.setEntity(new StringEntity(data.toString()));
		        HttpResponse response = client.execute(postRequest);
	        
		        // Response 출력
		        if (response.getStatusLine().getStatusCode() == 200) {
		            ResponseHandler<String> handler = new BasicResponseHandler();
		            String body = handler.handleResponse(response);
		            
		            jObject = (JSONObject) parser.parse(body);
		            jArray = (JSONArray) jObject.get("choices");		            
		            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message"); 
		            System.out.println("----------------------------------------");
		            System.out.println("Response 출력 : \n");
		            System.out.println(jobj.toString());
	
		            apiAnswer = (String) jobj.get("content");
	            
		            System.out.println(apiAnswer);
		            
		            // 정상 답변을 받았다면, 답변은 system에 저장하고 reqArray는 초기화
			        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
			        System.out.println("reqArry 출력 : ");
			        System.out.println(reqArry.toString());
			        // [{"role":"system","content":"You are a helpful assistant."},{"role":"user","content":"recommend movies"}]
			        
			        System.out.println(reqArry.get(0).toString());
			        // {"role":"system","content":"You are a helpful assistant."}
			        
			       
//			        reqArry.removeAll(reqArry);
//			        System.out.println(reqArry.toString());
			       
			        
			        System.out.println("마지막 reqArry 확인 =============================");
			        
			        answer.put("role", "system");
			        answer.put("content", apiAnswer);
			        reqArry.add(answer);
			        
			        assisAnswer.put("role", "assistant");
		            assisAnswer.put("content", apiAnswer);
		            reqArry.add(assisAnswer);
			        
			        System.out.println(reqArry.toString());
			        System.out.println("===============================================");

	            } else {
	            	
	            	// error > responsebody
	            	System.out.println("+++++++++++++bad requeset body 확인+++++++++++++");
	            	System.out.println(response.getStatusLine().getStatusCode());
	            	System.out.println(EntityUtils.toString(response.getEntity()));
	            	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	        
	            } // if-else 
	        
	        } //while
	            
		} catch (Exception e) {
				System.out.println("예외발생");
		} //trycath
		
	} //chatTurboV2
	

	// HttpClient + gpt-3.5-turbo + user, assis만 저장, 각각 json 3개로 만들고 cnt=3이면 초기화 하도록...
	public static String lastChatTest(String input) throws Exception { 
		
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
		return "";
	} // lastChatTest
	
	
} //httpClientTest
