package com.localhost.airpollutionapicall;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	// 서버에서 CORS 설정
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET})
    @RequestMapping(value = "{path:.+}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> corsRequest() {
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
    
    // ai-plugin.json 파일 읽기
    @GetMapping(value = "/.well-known/ai-plugin.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPluginManifest(HttpServletRequest request) throws IOException {
    	
    	String path = HomeController.class.getResource("").getPath();

    	if (path.substring(0,1).equals("/")) {
    		path = path.substring(1);
    	}
    	
    	String host = request.getHeader("Host");        
    	String text = new String(Files.readAllBytes(Paths.get(path+".well-known/ai-plugin.json")));
        text = text.replace("PLUGIN_HOSTNAME", "https://" + host);
        
        return ResponseEntity.ok().body(text);
        
    }
    
    @GetMapping(value = "/openapi.yaml", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> openapiSpec(HttpServletRequest request) throws IOException {
        String host = request.getHeader("Host");
        
        String path = HomeController.class.getResource("").getPath();

    	if (path.substring(0,1).equals("/")) {
    		path = path.substring(1);
    	}
        
    	String text = new String(Files.readAllBytes(Paths.get(path+"/openapi.yaml")));

        // This is a trick we do to populate the PLUGIN_HOSTNAME constant in the OpenAPI spec
        text = text.replace("PLUGIN_HOSTNAME", "https://" + host);
                
        return ResponseEntity.ok().body(text);
    }
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale) {				
		return "home";
	}
    
	
	@GetMapping(value = "/result.do")
	public String callApi(HomeVO vo, HttpServletRequest request) throws IOException {
		
		// api 호출
		String result = HomeService.callApi(vo.getCity());
		
		// jsp로 데이터 전달
		request.setAttribute("result", result);
		
		return "result";
	}

}