package com.abinandan.spamdetection.auth.config;

import com.abinandan.spamdetection.auth.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userPhoneNumber;

        //        String requestUri = request.getRequestURI();
        //
        //        if (request.getContentType() != null &&
        // request.getContentType().contains("application/json")) {
        //            // Read the JSON body into a Map
        //            Map body = objectMapper.readValue(request.getInputStream(), Map.class);
        //
        //            if (requestUri.startsWith("/api/v1/auth/register")) {
        //                // Extract fields from body
        //                String phoneNumber = (String) body.get("phoneNumber");
        //                String name = (String) body.get("firstName");
        //                String password = (String) body.get("password");
        //
        //                if (!StringUtils.hasLength(name) || !StringUtils.hasLength(phoneNumber) ||
        // !StringUtils.hasLength(password)) {
        //                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //                    response.getWriter().write("Phone number, Name and Password are
        // required");
        //                    response.getWriter().flush();
        //                    return; // Prevent further processing if validation fails
        //                }
        //
        //                UserDetails userDetails =
        // userDetailsService.loadUserByUsername(phoneNumber);
        //                if (userDetails != null) {
        //                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //                    response.getWriter().write("Phone number already exists");
        //                    response.getWriter().flush();
        //                    return;
        //                }
        //            }
        //
        //            if (requestUri.startsWith("/api/v1/auth/login")) {
        //                // Extract field from body
        //                String phoneNumber = (String) body.get("phoneNumber");
        //
        //                if (!StringUtils.hasLength(phoneNumber)) {
        //                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //                    response.getWriter().write("Phone number is required");
        //                    response.getWriter().flush();
        //                    return; // Prevent further processing if validation fails
        //                }
        //            }
        //        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        try {
            userPhoneNumber = jwtService.extractUserName(jwtToken);

            if (userPhoneNumber != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userPhoneNumber);

                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Invalid JWT token");
                    response.getWriter().flush();
                    return; // Stop further processing if token is invalid
                }
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Invalid JWT token");
                response.getWriter().flush();
                return; // Stop further processing if token is missing or invalid
            }

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid JWT token");
            response.getWriter().flush();
            return; // Stop further processing if an exception occurs
        }
        filterChain.doFilter(request, response);
    }
}
