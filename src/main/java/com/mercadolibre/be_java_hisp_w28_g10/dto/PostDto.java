package com.mercadolibre.be_java_hisp_w28_g10.dto;

import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private int id;
    private LocalDate date;
    private int category;
    private double price;
    private List<ProductDto> producDtotList;
    private boolean hasPromo;
    private double discount;
}
