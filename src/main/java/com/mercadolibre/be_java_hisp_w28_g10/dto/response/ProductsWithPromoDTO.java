package com.mercadolibre.be_java_hisp_w28_g10.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsWithPromoDTO {
    private int id;
    private String name;
    private int promoCount;
}
