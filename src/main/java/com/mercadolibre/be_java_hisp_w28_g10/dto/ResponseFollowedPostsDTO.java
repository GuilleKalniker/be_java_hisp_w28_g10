package com.mercadolibre.be_java_hisp_w28_g10.dto;

import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFollowedPostsDTO {
    private Integer userId;
    private List<ResponsePostNoPromoDTO> postDTOList;
}
