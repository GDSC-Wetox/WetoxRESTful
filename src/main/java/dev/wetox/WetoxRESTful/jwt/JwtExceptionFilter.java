package dev.wetox.WetoxRESTful.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import static dev.wetox.WetoxRESTful.jwt.JwtError.*;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            setErrorResponse(response, INVALID_JWT);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, ILLEGAL_JWT);
        } catch (Exception e) {
            setErrorResponse(response, UNEXPECTED_JWT);
        }
    }

    private void setErrorResponse(HttpServletResponse response, JwtError jwtError) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        response.setStatus(jwtError.getHttpStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(
                response.getWriter(),
                JwtErrorResponse.builder()
                        .timestamp(LocalDateTime.now().format(ISO_DATE_TIME))
                        .error(jwtError.getMessage())
                        .build());
    }
}
