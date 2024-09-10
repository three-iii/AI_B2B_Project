package com.three_iii.user.application;

import static com.three_iii.user.exception.ErrorCode.ACCESS_DENIED;
import static com.three_iii.user.exception.ErrorCode.DUPLICATE_SLACK_ID;
import static com.three_iii.user.exception.ErrorCode.DUPLICATE_USERNAME;

import com.three_iii.user.application.dto.SignUpRequestDto;
import com.three_iii.user.domain.Role;
import com.three_iii.user.domain.User;
import com.three_iii.user.domain.repository.UserRepository;
import com.three_iii.user.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String MASTER_TOKEN = "MASTER_TOKEN_AI_B2B_THREE_III";

    public Long signUp(SignUpRequestDto signUpRequestDto) {
        validate(signUpRequestDto);

        Role role = Role.CUSTOMER;
        if (signUpRequestDto.isMaster()) {
            if (!MASTER_TOKEN.equals(signUpRequestDto.getMasterToken())) {
                throw new ApplicationException(ACCESS_DENIED);
            }
            role = Role.MASTER_MANAGER;
        }
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        User user = signUpRequestDto.toEntity(encodedPassword, role);
        return userRepository.save(user).getId();
    }

    private void validate(SignUpRequestDto signUpRequestDto) {
        validateDuplicateUsername(signUpRequestDto.getUsername());
        validateDuplicateSlackId(signUpRequestDto.getSlackId());
    }

    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ApplicationException(DUPLICATE_USERNAME);
        }
    }

    private void validateDuplicateSlackId(String slackId) {
        if (userRepository.existsBySlackId(slackId)) {
            throw new ApplicationException(DUPLICATE_SLACK_ID);
        }
    }


}
