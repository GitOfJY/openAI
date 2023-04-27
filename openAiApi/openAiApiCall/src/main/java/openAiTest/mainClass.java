package openAiTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class mainClass {
	
	
	public static void main(String[] args) throws IOException {		
		
		callApi ca = new callApi();
		HttpClient client = HttpClientBuilder.create().build();		

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
					// 파일 대화 이용
					System.out.println("\n\n========================================\n");
					System.out.println("\n파일 대화 - 콘솔을 시작합니다.\n\n");
					System.out.println("========================================\n");                    	

					bf.write("\n\n========================================\n");
					bf.write("파일 대화 - 콘솔을 시작합니다.\n");
					bf.write("========================================\n\n"); 

					ca.callOpenAi(client, newFilePath, 1, bf);

					System.out.println("\n\n========================================\n");
					System.out.println("\n파일 대화 - 콘솔을 종료합니다.\n\n");
					System.out.println("========================================\n");                    	

					bf.write("\n\n========================================\n");
					bf.write("파일 대화 - 콘솔을 종료합니다.\n");
					bf.write("========================================\n\n");  

					break;

			    		case 2:
					// 사용자 대화 이용
					System.out.println("\n\n========================================\n");
					System.out.println("\n사용자 대화 - 콘솔을 시작합니다.\n\n");
					System.out.println("========================================\n\n");                    	

					bf.write("\n\n========================================\n");
					bf.write("사용자 대화 - 콘솔을 시작합니다.\n");
					bf.write("========================================\n\n"); 

					ca.callOpenAi(client, newFilePath, 2, bf);

					System.out.println("\n\n========================================\n");
					System.out.println("\n사용자 대화 - 콘솔을 종료합니다.\n\n");
					System.out.println("========================================\n\n");                    	

					bf.write("\n\n\n========================================\n");
					bf.write("사용자 대화 - 콘솔을 종료합니다.\n");
					bf.write("========================================\n\n"); 

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
				sc.close();
			}
        	}

		bf.write("\n\n\n\n\n----------------------------------------\n");
		bf.write(newFileName+"\n");
		bf.write("api_test 콘솔 종료합니다.\n\n");
		System.out.println("\n\n\n\n\n----------------------------------------\n");
		System.out.println("api_test 콘솔 종료합니다.\n\n");
		bf.flush();
		bf.close();
	}
}
