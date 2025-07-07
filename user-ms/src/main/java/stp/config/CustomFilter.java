package stp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import stp.model.User;
import stp.repository.UserRepository;
import stp.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomFilter extends OncePerRequestFilter {

    public static final String BEARER = "BEARER ";
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("CustomFilter");
        String header = request.getHeader("Authorization");
        log.info("Header: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(BEARER.length()).trim();
        log.info("Token: {}", token);

        Claims claims;
        try {
            claims = jwtService.parserToken(token);
        } catch (ExpiredJwtException e) {
            log.warn("Token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new RuntimeException("Token expired");
        } catch (Exception e) {
            log.warn("Token invalid: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new RuntimeException("Token expired");
        }

        String email = claims.get("email", String.class);
        User user = userRepository.findByEmail(email).orElseThrow();

        if (!user.isEnabled()) {
            log.warn("User is disabled: {}", user.getEmail());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("User is disabled");
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);
        if (authentication == null) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }

        filterChain.doFilter(request, response);
    }

}
