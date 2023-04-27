package openAiApiCall;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONTest {
	
	public static void main(String[] args) {	
		
		
		// 1.
		//JSONArrayTest();
		
		// 2.
		//JArrayDuplicationTest();

		// 3.
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		JSONObject json4 = new JSONObject();

		JSONArray jArray_first = new JSONArray();
		
		JSONArrayadd1(json1, json2, jArray_first);
		JSONArrayadd2(json1, json2, jArray_first);
		JSONArrayadd3(json3, json4, jArray_first);
		
		System.out.println("-----------------------------------------");
		System.out.println(jArray_first);
		
	}
	
	
	// JSONArray 덮어쓰기 test
	public static void JSONArrayTest() {
	
		// JSON 두개 생성 후 JSONArray에 넣기
		JSONObject json1 = new JSONObject();
		json1.put("happy", 1);
		JSONObject json2 = new JSONObject();
		json2.put("sky", 2);
		JSONObject json3 = new JSONObject();
		json3.put("toy", 3);
		JSONObject json4 = new JSONObject();
		json4.put("flower", 4);

		//JSONArray 생성
		JSONArray jArray_first = new JSONArray();
		jArray_first.add(json1);
		jArray_first.add(json2);
		
		JSONArray jArray_second = new JSONArray();
		jArray_second.add(json3);
		jArray_second.add(json4);
		
		System.out.println("첫 번재 JSONArray : ");
		System.out.println(jArray_first.toString());		
		System.out.println("두 번재 JSONArray : ");
		System.out.println(jArray_second.toString());
		
		// temp JSONArray 생성
		JSONArray jArray_temp = new JSONArray();
		
		// 교환
		jArray_temp = jArray_second;
		jArray_second = jArray_first;
		jArray_first = jArray_temp;
		
		System.out.println("++++++++++++결과++++++++++++");
		
		System.out.println("jArray_first : ");
		System.out.println(jArray_first.toString());
		System.out.println("jArray_second : ");
		System.out.println(jArray_second.toString());
		
	}
	
	
	public static void JArrayDuplicationTest() {
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		JSONArray jArray_first = new JSONArray();
		
		
		json1.put("role", 1);
		json1.put("content", "1 - content");
		jArray_first.add(json1);
		
		System.out.println("1 : " + jArray_first.toString());
		
		
		json1.put("role", 1);
		json1.put("content", "2 - content");
		jArray_first.add(json1);
		
		System.out.println("2 : " + jArray_first.toString());
		
		json2.put("role", 1);
		json2.put("content", "3 - content");
		jArray_first.add(json2);
		
		System.out.println("4 : " + jArray_first.toString());	
		
		json3.put("role", 1);
		json3.put("content", "4 - content");
		jArray_first.add(json3);
		
		System.out.println("4 : " + jArray_first.toString());		
			
	}
	
	// 만약에 JSONArray를 main에서 만들고 메서드 2개가 사용하면 한 번에 저장 test
	public static void JSONArrayadd1(JSONObject json1, JSONObject json2, JSONArray jArray_first) {
		
		System.out.println("==========JSONArrayadd1==========");
		
		json1.put("role", 1);
		json1.put("content", "1-1 - content");
		jArray_first.add(json1);
		
		System.out.println("1 : " + jArray_first.toString());
		
		json2.put("role", 1);
		json2.put("content", "1-2 - content");
		jArray_first.add(json2);
		
		System.out.println("2 : " + jArray_first.toString());			
		
	}
	
	public static void JSONArrayadd2(JSONObject json1, JSONObject json2, JSONArray jArray_first) {
		
		System.out.println("==========JSONArrayadd2==========");
		
		json1.put("role", 1);
		json1.put("content", "2-1 - content");
		jArray_first.add(json1);
		
		System.out.println("1 : " + jArray_first.toString());
		
		json2.put("role", 1);
		json2.put("content", "2-2 - content");
		jArray_first.add(json2);
		
		System.out.println("2 : " + jArray_first.toString());			
		
	}
	
	public static void JSONArrayadd3(JSONObject json3, JSONObject json4, JSONArray jArray_first) {
		
		System.out.println("==========JSONArrayadd3==========");
		
		json3.put("role", 1);
		json3.put("content", "3-1 - content");
		jArray_first.add(json3);
		
		System.out.println("1 : " + jArray_first.toString());
		
		json4.put("role", 1);
		json4.put("content", "3-2 - content");
		jArray_first.add(json4);
		
		System.out.println("2 : " + jArray_first.toString());			
		
	}
	

}
