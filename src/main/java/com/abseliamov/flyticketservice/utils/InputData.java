package com.abseliamov.flyticketservice.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public enum InputData {
    STRING(input -> true, ""),
    INTEGER(input -> StringUtils.isNumeric(input), "Please enter a number"),
    DATE(input -> {
        int numberDays = 31;
        boolean correctDate = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate userDate = LocalDate.parse(input, formatter);
            LocalDate beforeDate = LocalDate.now().plusDays(numberDays);
            if (userDate.compareTo(LocalDate.now()) >= 0 && beforeDate.compareTo(userDate) >= 0) {
                correctDate = true;
            } else {
                System.out.println("Search is possible from: " + LocalDate.now().format(formatter)
                        + " to: " + beforeDate.format(formatter));
            }
        } catch (Exception e) {
            System.out.println("Enter correct date format dd.MM.yyyy: ");
        }
        return correctDate;
    }, "Please enter date in format dd.MM.yyyy");

    private Predicate<String> value;
    private String errorMessage;

    InputData(Predicate<String> data, String errorMessage) {
        this.value = data;
        this.errorMessage = errorMessage;
    }

    public Predicate<String> getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
