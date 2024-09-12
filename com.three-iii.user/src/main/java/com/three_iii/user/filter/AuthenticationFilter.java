package com.three_iii.user.filter;

import com.three_iii.user.domain.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String id = request.getHeader("X-User-Id");
        String username = request.getHeader("X-User-Name");
        String role = request.getHeader("X-User-Role");

        if (id != null && username != null && role != null) {
            UserPrincipal userPrincipal = UserPrincipal.of(Long.valueOf(id), username, role);

            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, List.of(authority)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return StringUtils.startsWithAny(request.getRequestURI(), "/api/auth/sign-in",
            "api/auth/sign-up");
    }
}
