package com.mercadolibre.be_java_hisp_w28_g10.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostNoPromoDTO {
    @JsonProperty("user_id")
    private int id;
    private String date;
    private int category;
    private double price;
    private ProductDTO product;
}
