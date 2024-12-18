package com.mercadolibre.be_java_hisp_w28_g10.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFollowedPostsDTO {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("posts")
    private List<PostDTO> postDTOList;
}
