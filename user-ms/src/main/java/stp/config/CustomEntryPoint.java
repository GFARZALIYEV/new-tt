package stp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import stp.exception.ErrorMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .status(HttpServletResponse.SC_CONFLICT)
                .message(Map.of("error",authException.getMessage()))
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(errorMessage));
    }
}
