package com.lj.testing.util;

import java.io.*;

public class BaseFileUtils {

    public static void writer(String filePath, String fileContent, boolean append) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(System.getProperty("line.separator"));
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String reader(String filePath) {
        StringBuffer fileContent = new StringBuffer();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

}
