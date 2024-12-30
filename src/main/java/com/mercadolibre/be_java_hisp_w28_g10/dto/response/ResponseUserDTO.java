package com.mercadolibre.be_java_hisp_w28_g10.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDTO {
    @JsonProperty("user_id")
    private int id;
    @JsonProperty("user_name")
    private String name;
}