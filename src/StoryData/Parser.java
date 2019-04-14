package StoryData;

import java.io.*;
import java.util.ArrayList;

public class Parser {
    public String txtFile;
    //the txt file it will attempt to retrieve;

    public Parser(String txtFileName) {
        this.txtFile = "src/StoryData/" + txtFileName;
        retrieve();
    }

    public ArrayList<String> retrieve() {
        try {
            FileReader fr = new FileReader(txtFile);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> storyData = new ArrayList<>();
            String str;
            while ((str = br.readLine()) != null) {
                storyData.add(str);
            }
            System.out.println(storyData);
            return storyData;
            
        } catch (IOException e) { 
            System.err.println("File " + txtFile + System.getProperty("user.dir") +" not found");
            return null;
        }
    }
}