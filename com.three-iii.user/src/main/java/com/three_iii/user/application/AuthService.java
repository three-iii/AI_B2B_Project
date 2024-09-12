package com.three_iii.user.application;

import static com.three_iii.user.exception.ErrorCode.ACCESS_DENIED;
import static com.three_iii.user.exception.ErrorCode.BAD_CREDENTIALS;
import static com.three_iii.user.exception.ErrorCode.DUPLICATE_SLACK_ID;
import static com.three_iii.user.exception.ErrorCode.DUPLICATE_USERNAME;

import com.three_iii.user.application.dto.SignInRequest;
import com.three_iii.user.application.dto.SignUpRequest;
import com.three_iii.user.domain.Role;
import com.three_iii.user.domain.User;
import com.three_iii.user.domain.repository.UserRepository;
import com.three_iii.user.exception.ApplicationException;
import com.three_iii.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final String MASTER_TOKEN = "MASTER_TOKEN_AI_B2B_THREE_III";

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        validate(signUpRequest);

        Role role = Role.CUSTOMER;
        if (signUpRequest.isMaster()) {
            if (!MASTER_TOKEN.equals(signUpRequest.getMasterToken())) {
                throw new ApplicationException(ACCESS_DENIED);
            }
            role = Role.MASTER_MANAGER;
        }
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user = signUpRequest.toEntity(encodedPassword, role);
        return userRepository.save(user).getId();
    }


    public String signIn(SignInRequest signInRequest) {
        User verifiedUser = verifyUser(signInRequest);
        return jwtUtil.createToken(
            verifiedUser.getId(),
            verifiedUser.getUsername(),
            verifiedUser.getRole().getDescription()
        );
    }

    private User verifyUser(SignInRequest signInRequest) {
        User user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(
            () -> new ApplicationException(BAD_CREDENTIALS)
        );
        checkPassword(user, signInRequest.getPassword());
        return user;

    }

    private void checkPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(BAD_CREDENTIALS);
        }
    }

    private void validate(SignUpRequest signUpRequest) {
        validateDuplicateUsername(signUpRequest.getUsername());
        validateDuplicateSlackId(signUpRequest.getSlackId());
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
