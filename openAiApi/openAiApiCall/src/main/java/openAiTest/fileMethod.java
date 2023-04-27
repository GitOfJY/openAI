package openAiTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class fileMethod {

	// 파일 리스트 출력
    public int file_list(String path) {
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
    

    // 파일 이름
    public String find_file_name(String path, int fileNum) {
        File dir = new File(path);
        File[] fileList = dir.listFiles();
        String filename = fileList[fileNum].getName();
        
        return filename;
    }
    
    // 파일 내용 읽기
    public String read_file(String readFilePath, String fileName) throws IOException {
        File file = new File(readFilePath+fileName);
        
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(file), "UTF-8")
        );

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        } 	
        
    	return sb.toString();
    }
	
}
