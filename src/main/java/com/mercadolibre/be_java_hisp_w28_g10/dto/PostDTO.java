package com.mercadolibre.be_java_hisp_w28_g10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String date;
    private int category;
    private double price;
    private ProductDTO productDto;
    private boolean hasPromo;
    private double discount;

    public static LocalDate parseStringToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The date format is invalid, expected: dd-MM-yyyy: " + date);
        }
    }
}
