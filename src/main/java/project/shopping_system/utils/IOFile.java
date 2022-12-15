package project.shopping_system.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    public static List<String> readFile(String pathFile) {
        List<String> raw = new ArrayList<>();
        try{
            File file = new File(pathFile);
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String line; //line = null;
            while ((line = readFile.readLine())!=null && !line.trim().isEmpty()){
                raw.add(line);
            }
            readFile.close();
        }catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return raw;
    }
    public static <T> void writeFile(List<T> list, String pathFile) {
        try{
            File file = new File(pathFile);
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (Object item : list){
                printWriter.print(item.toString());
            }
            printWriter.close();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
