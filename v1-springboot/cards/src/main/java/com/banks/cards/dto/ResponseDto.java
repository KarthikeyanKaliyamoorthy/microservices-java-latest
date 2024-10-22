package com.banks.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO", name = "Response")
public class ResponseDto {

    @Schema(description = "Status code")
    private String statusCode;
    @Schema(description = "Status message")
    private String statusMsg;

}
