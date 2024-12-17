package com.mercadolibre.be_java_hisp_w28_g10.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCsvPostDTO {
    private int id;
    private String date;
    private int category;
    private double price;
    private ProductDTO product;
    private boolean hasPromo;
    private double discount;
}
