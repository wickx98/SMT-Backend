package com.smt.QA.configs;

import com.smt.QA.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        // If no Authorization header or invalid Bearer token, continue the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token from the Authorization header
            final String jwt = authHeader.substring(7);  // Remove "Bearer " prefix
            final String userEpfNumber = jwtService.extractUsername(jwt);

            // Log the received JWT for debugging purposes
            System.out.println("Authorization Header: " + authHeader);
            System.out.println("Extracted username (EPF number): " + userEpfNumber);

            // Get current authentication context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If userEpfNumber is not null and authentication is not already set, authenticate the user
            if (userEpfNumber != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEpfNumber);

                // Validate the JWT token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // If valid, create authentication token and set it in the security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Log success
                    System.out.println("Authentication successful for user: " + userEpfNumber);
                } else {
                    // Log invalid token scenario
                    System.out.println("Invalid token detected: " + jwt);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                    return; // Stop the filter chain if token is invalid
                }
            }

            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            // Specific exception handling for expired token
            System.out.println("Token has expired: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired token");
        } catch (MalformedJwtException ex) {
            // Specific exception handling for malformed token
            System.out.println("Malformed token: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Malformed token");
        } catch (Exception exception) {
            // General error handling for other exceptions
            System.out.println("Error during authentication: " + exception.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
