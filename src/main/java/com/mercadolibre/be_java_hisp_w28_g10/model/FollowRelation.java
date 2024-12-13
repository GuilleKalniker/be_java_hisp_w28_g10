package com.mercadolibre.be_java_hisp_w28_g10.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRelation {
    private int idFollower; // ID del seguidor
    private int idFollowed; // ID de la persona seguida
    //private String date;
    private String date;
}



