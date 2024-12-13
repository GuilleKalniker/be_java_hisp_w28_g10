package com.mercadolibre.be_java_hisp_w28_g10.dto.response;

import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostNoPromoDTO {
    private int id;
    private String date;
    private int category;
    private double price;
    private ProductDTO productDto;
}
