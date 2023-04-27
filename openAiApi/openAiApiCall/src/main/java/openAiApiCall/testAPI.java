package openAiApiCall;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;

public class testAPI {
	
	public static void main(String[] args) throws Exception {		
		
		// openai 라이브러리 사용했을 때
		String input = "Y";
		
		while (!input.equals("N")) {
//			Scanner sc = new Scanner(System.in);
//			input = sc.nextLine();
//			chatGPTV5(input);
			chatGPTV4();
		}
		
	}
	
	public static String chatGPTV4() throws Exception {
    	
   	    String token = "인증키";
        OpenAiService service = new OpenAiService(token);

        System.out.println("\nCreating completion...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt("Somebody once told me the world is gonna roll me")
                .echo(true)
                .user("testing")
                .n(3)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);

        System.out.println("\nCreating Image...");
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt("A cow breakdancing with a turtle")
                .build();

        System.out.println("\nImage is located at:");
        System.out.println(service.createImage(request).getData().get(0).getUrl());
   	
   	return "";
   	
   }
	
	public static String chatGPTV5(String input) throws Exception {
    	
   	    String token = "인증키";
        OpenAiService service = new OpenAiService(token);

        System.out.println("Streaming chat completion...");
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), input);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .logitBias(new HashMap<>())
                .build();
        
        System.out.println(chatCompletionRequest.toString());

        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(System.out::println);
   	
   	return "";
   
   }

}
