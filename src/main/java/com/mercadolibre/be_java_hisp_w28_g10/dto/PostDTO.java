package com.mercadolibre.be_java_hisp_w28_g10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
