package com.abseliamov.flyticketservice.utils;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class IOUtil {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String getValidInputData(String message, InputData typeData) {
        System.out.println(message);
        String inputData = null;

        try {
            inputData = getReader().readLine();
            while (!typeData.getValue().test(inputData)) {
                System.out.println(typeData.getErrorMessage());
                inputData = getReader().readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading input data from console " + e);
        }
        return inputData;
    }

    public static BufferedReader getReader() {
        return reader;
    }

    public static <T> void printMenuHeader(List<T> header) {
        if (header != null) {
            header.forEach(System.out::println);
        }
    }

    public static <T> void printMenuItem(List<T> menu) {

        if (menu != null) {
            System.out.println("*********************************");
            for (int i = 0; i < menu.size(); i++) {
                if (i == 0) {
                    System.out.println("\t" + menu.get(i) + "\n*********************************");
                    continue;
                }
                if (i == menu.size() - 1) {
                    System.out.println(0 + ". " + menu.get(i)
                            + "\n*********************************");
                } else {
                    System.out.println(i + ". " + menu.get(i));
                }
            }
        }
    }

    public static boolean validateNumberSize(int min, int max) {
        return min >= 0 && min <= (max - 2);
    }

    public static File checkFileExists(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                System.out.println("Exception create file \'" + fileName + "\' " + e);
            }
        }
        return file;
    }

    public static File getFile(String fileName) {
        Properties property = new Properties();
        try {
            property.load(new FileReader("src/main/resources/properties/file.properties"));
        } catch (IOException e) {
            System.out.println("Error reading file properties " + e);
        }
        String resourceFile = property.getProperty(fileName);
        File file = IOUtil.checkFileExists(resourceFile);
        return file;
    }
}
