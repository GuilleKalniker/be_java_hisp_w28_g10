package com.mercadolibre.be_java_hisp_w28_g10.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;

}
