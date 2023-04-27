package openAiApiCall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class mainPage {
	
	public static void main(String[] args) throws IOException {		
		
		Scanner sc = new Scanner(System.in);
        String startOrStop = "Y";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd(HH-mm-ss)");

        String newFileName = formatter.format(calendar.getTime()).toString();


        File file = new File("C:\\resultTxt\\"+newFileName+".txt");
        String newFilePath = "C:\\resultTxt\\"+newFileName+".txt";

        BufferedWriter bf = new BufferedWriter(new FileWriter(newFilePath, true));

        System.out.println("\n\n========================================\n");
        System.out.println("api_test 콘솔을 시작합니다.\n");
        System.out.println("========================================\n");

        while (startOrStop.toUpperCase().equals("Y")) {

            System.out.println("\n1 또는 2를 입력해 주세요.");
            System.out.println("(1: 파일 대화, 2: 사용자 입력 대화)");

            try {

                int inputnum = sc.nextInt();

                switch(inputnum) {
                    case 1: 
                    	// txt 파일 이름
                        String readRilePath = "C:\\apiTestTxt\\";
                            
                        int file_list_length = file_list(readRilePath);

                        System.out.println("해당하는 파일 번호를 입력하세요.");
                        int fileNum = sc.nextInt();

                        if (fileNum > file_list_length ||  fileNum < 0) {
                            System.out.println("범위에 벗어나는 번호 입니다. \n\n");
                            break;
                        }
                        
                        // 파일 대화 이용
                        String fileName = find_file_name(readRilePath, fileNum);
                        talk_file(newFilePath, fileName, readRilePath);
                        break;
                    case 2:
                        // 사용자 대화 이용
                        talk_customer(newFilePath);
                        break;
                    default :
                        System.out.println("\n 1 또는 2를 입력해 주세요.");
                }
                    
            } catch (InputMismatchException e) {
                System.out.println("\n정수가 아닙니다. 1 또는 2를 입력해 주세요.");
            }
            
            System.out.println("api_test 계속 진행하려면 Y를 입력해 주세요.");
            sc.nextLine();
            String input = sc.nextLine();

            if (!input.toUpperCase().equals("Y")) {
                startOrStop = "N";
                System.out.println("========================================\n\n");
                System.out.println("api_test 콘솔 종료합니다.\n\n");

                sc.close();
            }
        }

        bf.write("\n\n\n\n\n----------------------------------------\n");
        bf.write(newFileName+"\n");
        bf.write("api_test 콘솔 종료합니다.\n\n");
        bf.flush();
        bf.close();
    }


    // 1. txt 파일 대화
    public static void talk_file(String newFilePath, String fileName, String readFilePath) throws IOException {

        BufferedWriter bf = new BufferedWriter(new FileWriter(newFilePath, true));
        bf.newLine();

        System.out.println("\n\n========================================\n");
        System.out.println("api_test - 1. 파일 대화 콘솔 시작합니다.\n");
        System.out.println("파일명 : "+fileName);
        System.out.println("----------------------------------------\n\n\n");

        bf.write("\n\n========================================\n");
        bf.write("api_test - 1.  파일 대화 콘솔 시작합니다.\n");
        bf.write("파일명  : "+fileName+"\n");
        bf.write("----------------------------------------\n\n\n");

        File file = new File(readFilePath+fileName);
        
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(file), "UTF-8")
        );

        String line;
        int cnt = 0;

        while ((line = reader.readLine()) != null) {
            sb.append(line + System.lineSeparator());
            cnt ++;
        }

        if (cnt==0) { 
            System.out.println("파일의 내용이 존재하지 않습니다. \n");
            bf.write("파일의 내용이 존재하지 않습니다.\n");

        } else {
            System.out.println("사용자 : \n\n"+sb);
            bf.write("사용자 :\n\n"+sb+"\n");

            try {
                //String apiAnswer = chatGPT(sb.toString());
                String apiAnswer = chatGPTV2("hello");
                System.out.println("api : \n"+apiAnswer);
                bf.write("api : \n"+apiAnswer+"\n");
            } catch (Exception e) {
                System.out.println("예외발생");
                bf.write("api : 예외발생\n");
            }
        }

        System.out.println("\n\n----------------------------------------\n");
        System.out.println("api_test - 파일 대화 콘솔 종료합니다.\n");
        System.out.println("=======================================\n");

        bf.write("\n\n----------------------------------------\n");
        bf.write("api_test - 파일 대화 콘솔 종료합니다..\n");
        bf.write("========================================\n");

        bf.flush();
        bf.close();

        reader.close();
    }

    // 2. 사용자 입력 대화
    public static void talk_customer(String newFilePath) throws IOException {

        String customerAnswer = "Y";

        BufferedWriter bf = new BufferedWriter(new FileWriter(newFilePath, true));
        bf.newLine();

        System.out.println("\n\n=======================================\n");
        System.out.println("api_test - 2. 사용자 대화 콘솔 시작합니다.\n");
        System.out.println("----------------------------------------\n\n\n");

        bf.write("\n\n========================================\n");
        bf.write("api_test - 2. 사용자 대화 콘솔 시작합니다.\n");
        bf.write("----------------------------------------\n\n\n");

        System.out.println("질문을 입력해 주세요. ");
        System.out.println("(대화를 종료하려면 N을 입력해 주세요.)\n");

        while (customerAnswer.toUpperCase().equals("Y")) {

            Scanner scan = new Scanner(System.in);
            System.out.println("사용자 : ");
            System.out.println("");
            String input = scan.nextLine();

                if (input.toUpperCase().equals("N")) {
                    
                    System.out.println("\n\n----------------------------------------\n");
                    System.out.println("api_test - 사용자 대화 콘솔 종료합니다.\n");
                    System.out.println("========================================\n");

                    bf.write("\n\n----------------------------------------\n");
                    bf.write("api_test - 사용자 대화 콘솔 종료합니다.\n");
                    bf.write("========================================\n");

                    customerAnswer = "N";

                } else {

                    bf.write("\n사용자 : \n\n"+input+"\n");

                    if (input.equals("")) {
                        System.out.println("질문을 입력하세요.\n");
                        bf.write("질문을 입력하세요.\n");
                    } else {                      
                        try {
                        	String apiAnswer = chatGPTV3(input);
                            System.out.println("\napi : "+apiAnswer+"\n\n");
                            bf.write("\napi : "+apiAnswer+"\n");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println(e.toString());
                        	System.out.println("예외발생");
                            
                            e.printStackTrace();
                        }
                    }
                }
            }
            bf.flush();
            bf.close();
        }

    public static int file_list(String path) {
        File dir = new File(path);
        File[] fileList = dir.listFiles();

        System.out.println("\n\n=======================================");
        System.out.println("파일 목록");
        System.out.println("----------------------------------------\n");

        for (int i=0; i<fileList.length; i++) {
            File file = fileList[i];
            System.out.println(i+". "+file.getName());
        }

        System.out.println("\n=======================================\n\n");
        return fileList.length;
    }
    

    public static String find_file_name(String path, int fileNum) {
        File dir = new File(path);
        File[] fileList = dir.listFiles();
        String filename = fileList[fileNum].getName();

        if (fileNum > fileList.length || fileNum < 0) {
            System.out.println("범위에 존재하지 않는 파일 번호입니다.");
            return "0"; 
        } 
        return filename;
    }

    
    // text-davinci-003
    public static String chatGPT(String text) throws Exception {

        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        try {
            
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer 인증키");

            JSONObject data = new JSONObject();
            data.put("model", "text-davinci-003");
            data.put("prompt", text);
            data.put("max_tokens", 4000);
            data.put("temperature", 1.0);

            con.setDoOutput(true);
            con.getOutputStream().write(data.toString().getBytes("UTF-8"));

            String output = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")).lines()
                    .reduce((a, b) -> a + b).get();
            
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(output);
            JSONArray jArray = (JSONArray) jObject.get("choices");
            JSONObject jobj = (JSONObject)jArray.get(0);
            String apiAnswer = (String)jobj.get("text");
            
            con.disconnect();
            
            return apiAnswer;

        } catch (Exception e) {
            con.getErrorStream();
            throw e;     
        }
    
    }
    
    
    // gpt-3.5-turbo
    public static String chatGPTV2(String text) throws Exception {

        String url = "https://api.openai.com/v1/chat/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        try {
            
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer 인증키");

            JSONArray reqArry = new JSONArray();
                        
            // system answer
            JSONObject answer = new JSONObject();
            
            // 사용자 입력 텍스트
            JSONObject inputMessage = new JSONObject();
            
            // system answer assis
            JSONObject assisAnswer = new JSONObject();    

            // 데이터 전송용
            JSONObject data = new JSONObject();
            
            String output = "";
            int i = 0;
            
            JSONParser parser = new JSONParser();
            JSONObject jObject;            
            JSONArray jArray;   
            JSONObject jobj;  
            String apiAnswer;
            
            Scanner scan = new Scanner(System.in);
                             
            while (!text.equals("N")) {
            	
            	if (i==0) {
            		answer.put("role", "system");
                    answer.put("content", "You are a helpful assistant.");
                    reqArry.add(answer);
            	}
            	
            	if (i!=0) {
                	
                	System.out.println("사용자 : ");
                    System.out.println("");
                    text = scan.nextLine();
                } 
                
                inputMessage.put("role", "user");
                inputMessage.put("content", text);
                reqArry.add(inputMessage);
                                       
                data.put("model", "gpt-3.5-turbo");
                data.put("messages", reqArry);
                data.put("max_tokens", 4000);
                data.put("temperature", 1.0); 
                
                if (i==0) { 
                	con.setDoOutput(true);
                	con.getOutputStream().write(data.toString().getBytes("UTF-8"));
                }
                
                // 여기가 문제네....
                // con.getOutputStream().write(data.toString().getBytes("UTF-8"));
                // Cannot write output after reading input.
                // java.net.ProtocolException: Cannot write output after reading input.
                
                // responsebody 획득
                output = getResponsebody(con);
                System.out.println("output1=" + output);
                            
                // responsebody > api 답변 추출
//                JSONParser parser = new JSONParser();
//                JSONObject jObject = (JSONObject) parser.parse(output);            
//                JSONArray jArray = (JSONArray) jObject.get("choices");   
//                JSONObject jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message");  
//                String apiAnswer = (String) jobj.get("content");
                
                //Unexpected token END OF FILE at position 0.
                
                jObject = (JSONObject) parser.parse(output);            
                jArray = (JSONArray) jObject.get("choices");   
                jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message");  
                apiAnswer = (String) jobj.get("content");
                
                System.out.println(apiAnswer);
                
                output = getResponsebody(con);
                System.out.println("output2=" + output);
                
                // 예외발생
                assisAnswer.put("role", "assistant");
                assisAnswer.put("content", apiAnswer);
                reqArry.add(assisAnswer);
                
                i++;
            	
            }            
            
            con.disconnect();
            
            return "";

        } catch (Exception e) {
            con.getErrorStream();
            e.getMessage();
            e.toString();
            throw e;     
        }
    
    }
    
    
    // gpt-3.5-turbo
    public static String chatGPTV3(String text) throws Exception {
    	
    	// 이것도 마찬가지로 con이 끊겨서 전 내용을 기억하지 못함...
    	// 만약에 parameter로 assistent 문장을 넣어준다면,,,

        String url = "https://api.openai.com/v1/chat/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        try {
            
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer 인증키");

            JSONArray reqArry = new JSONArray();
                        
            JSONObject answer = new JSONObject();
            JSONObject inputMessage = new JSONObject();
            JSONObject assisAnswer = new JSONObject();    
            JSONObject data = new JSONObject();
            
            String output = "";
            int i = 0;
            
            JSONParser parser = new JSONParser();
            JSONObject jObject;            
            JSONArray jArray;   
            JSONObject jobj;  
            String apiAnswer;
  
            answer.put("role", "system");
            answer.put("content", "You are a helpful assistant.");
            reqArry.add(answer);
                         
                
            inputMessage.put("role", "user");
            inputMessage.put("content", text);
            reqArry.add(inputMessage);

                            
            data.put("model", "gpt-3.5-turbo");
            data.put("messages", reqArry);
            data.put("max_tokens", 4000);
            data.put("temperature", 1.0); 
                               
            con.setDoOutput(true);
            con.getOutputStream().write(data.toString().getBytes("UTF-8"));
                
            output = getResponsebody(con);
            System.out.println("output1=" + output);
                
            jObject = (JSONObject) parser.parse(output);            
            jArray = (JSONArray) jObject.get("choices");   
            jobj = (JSONObject) ((JSONObject)jArray.get(0)).get("message");  
            apiAnswer = (String) jobj.get("content");
                
            System.out.println(apiAnswer);
                
            output = getResponsebody(con);
            System.out.println("output2=" + output);
                
            assisAnswer.put("role", "assistant");
            assisAnswer.put("content", apiAnswer);
            reqArry.add(assisAnswer);
           
            con.disconnect();
            
            return "";

        } catch (Exception e) {
            con.getErrorStream();
            e.getMessage();
            e.toString();
            throw e;     
        }
    
    }
    
    
    // responsebody 받기
    public static String getResponsebody(HttpURLConnection con) { 
    	
    	try {
    		
    		int responseCode = con.getResponseCode();
            BufferedReader br;
            
            if (responseCode==200) {
            	br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
            	br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = br.readLine())!=null) {
            	response.append(inputLine);
            }
            
            // 결과물 출력
            String output = response.toString();
            return output;
			
		} catch (Exception e) {
			return "예외발생";
		}
    	
    }

    // public static int find_num(String path, LocalDate date) { 
    //     int cnt = 0;

    //     File dir = new File(path);
    //     File[] fileList = dir.listFiles();

    //     for (int i=0; i<fileList.length; i++) {
    //         File file = fileList[i];

    //         if (file.getName().contains(date.toString())) {
    //             cnt ++;
    //         }
    //     }
    //     return cnt;
    // }

}