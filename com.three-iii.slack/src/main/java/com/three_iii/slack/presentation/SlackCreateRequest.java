package com.three_iii.slack.presentation;

import com.three_iii.slack.application.SlackDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SlackCreateRequest {

    @NotBlank
    String receiptId;
    @NotBlank
    String message;

    public SlackDto toDTO() {
        return SlackDto.create(this.receiptId, this.message);
    }
}
