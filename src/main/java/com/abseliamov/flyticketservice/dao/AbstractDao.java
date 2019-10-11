package com.abseliamov.flyticketservice.dao;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {

    abstract void create(T item);

    abstract T getById(long id);

    abstract void update(T item);

    abstract void delete(T item);

    abstract T parseDataFromFile(String[] data);

    abstract StringBuilder buildDataToFile(List<T> listItems);

    List<T> getAll(File file, String fileHeader) {
        List<T> listItem = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = reader.readLine()) != null) {
                if (data.equals(fileHeader)) {
                    continue;
                }
                String[] dataLine = data.split(",");
                listItem.add(parseDataFromFile(dataLine));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found " + e);
        } catch (IOException e) {
            System.out.println("Error read from file " + file.getName() + " " + e);
        }
        return listItem;
    }

    void writeToFile(File file, List<T> listItems) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(buildDataToFile(listItems).toString());
        } catch (
                IOException e) {
            System.out.println("Error write to file " + file.getName() + " " + e);
        }
    }

    public LocalDateTime parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }
}
