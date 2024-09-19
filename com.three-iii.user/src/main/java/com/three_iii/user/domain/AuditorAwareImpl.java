package com.three_iii.user.domain;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserPrincipal) {
                    return ((UserPrincipal) authentication.getPrincipal()).getUsername();
                }
                return null;
            });
    }
}
