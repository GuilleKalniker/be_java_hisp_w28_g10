package com.mercadolibre.be_java_hisp_w28_g10.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @NotNull(message = "El id no puede estar vacío.")
    @Positive(message = "El id debe ser mayor a cero.")
    @JsonProperty("user_id")
    private int id;

    @JsonProperty("post_id")
    private int postId;

    @NotNull(message = "La fecha no puede estar vacía.")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])-(0[1-9]|1[0-2])-(\\d{4})$",
            message = "La fecha debe estar en el formato dd-MM-yyyy.")
    private String date;

    @NotNull(message = "El campo no puede estar vacío.")
    private int category;

    @NotNull(message = "El campo no puede estar vacío.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio no puede ser 0.")
    @DecimalMax(value = "10000000.0", message = "El precio máximo por producto es de 10.000.000.")
    private double price;

    @Valid
    private ProductDTO product;

    @JsonProperty("has_promo")
    private boolean hasPromo;

    private double discount;
}
