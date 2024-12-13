package com.mercadolibre.be_java_hisp_w28_g10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRelationDTO {
    private int idFollower; // ID del seguidor
    private int idFollowed; // ID de la persona seguida
}