package com.mercadolibre.be_java_hisp_w28_g10.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithCountDTO {
    @CsvBindByName(column = "ID")
    private int id;
    @CsvBindByName(column = "NAME")
    private String name;
    @CsvBindByName(column = "COUNT")
    private int count;
}
