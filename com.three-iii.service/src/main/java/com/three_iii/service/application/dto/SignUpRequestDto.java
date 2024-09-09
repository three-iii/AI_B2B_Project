package com.three_iii.service.application.dto;

import com.three_iii.service.domain.Role;
import com.three_iii.service.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    private String username;
    private String password;
    private String slackId;
    private boolean master = false;
    private String masterToken = "";

    public User toEntity(String encodedPassword, Role role) {
        return User.of(username, encodedPassword, role, slackId);
    }

}
