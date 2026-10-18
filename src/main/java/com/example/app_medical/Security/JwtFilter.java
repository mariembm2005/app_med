package com.example.app_medical.Security;
// Declares the package this class belongs to

import jakarta.servlet.*;
// Imports core servlet interfaces (Filter, FilterChain, ServletException, etc.) — the general servlet API

import jakarta.servlet.ServletException;
// Explicit import of ServletException (redundant here since it's already covered by the wildcard above, but harmless)

import jakarta.servlet.http.*;
// Imports HTTP-specific servlet classes: HttpServletRequest, HttpServletResponse, etc.

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// Imports the class used to represent an "authenticated" principal inside Spring Security's context

import org.springframework.security.core.context.SecurityContextHolder;
// Imports the holder that stores the current request's security/authentication state, accessible anywhere during the request

import org.springframework.stereotype.*;
// Imports Spring stereotype annotations (wildcard) — here specifically used for @Component

import org.springframework.web.filter.OncePerRequestFilter;
// Imports the base class that guarantees this filter's logic runs exactly once per request (avoids duplicate filtering in forward/include scenarios)

import java.io.*;
// Imports IO classes (wildcard) — needed for IOException in the method signature

import java.util.*;
// Imports java.util classes (wildcard) — here used for List

@Component
// Registers this class as a Spring bean, so Spring can instantiate it, inject its dependencies, and wire it into the security filter chain

public class JwtFilter extends OncePerRequestFilter {
// Declares the filter class, extending Spring's OncePerRequestFilter so its logic runs once per incoming request

    private final JwtUtil jwtUtil;
    // Holds a reference to JwtUtil — used to decode/validate incoming tokens; final because it's set once via constructor

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    // Constructor injection: Spring automatically passes in the JwtUtil bean when creating this filter

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Overrides the method that runs once per request; receives the request, response, and the chain to continue processing afterward

        String authHeader = request.getHeader("Authorization");
        // Reads the "Authorization" header from the incoming request (e.g., "Bearer eyJhbGc...") — null if not sent

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Only proceeds with token logic if the header exists AND follows the expected "Bearer <token>" format

            String token = authHeader.substring(7);
            // Strips off the first 7 characters ("Bearer ") to isolate just the raw JWT string

            String username = jwtUtil.extractIdentity(token);
            // Decodes and verifies the token via JwtUtil, pulling out the subject (username/identity) — throws an exception if invalid/expired (not caught here!)

            var auth = new UsernamePasswordAuthenticationToken(
                    username, null, List.of());
            // Creates an authentication object: principal = username, credentials = null (not needed post-login), authorities = empty list (no roles/permissions granted)

            SecurityContextHolder.getContext().setAuthentication(auth);
            // Stores this authentication into the current request's security context — marks the request as "authenticated" for downstream checks
        }

        filterChain.doFilter(request, response);
        // Passes control to the next filter in the chain (or the controller, if this was the last filter) — runs regardless of whether authentication succeeded above
    }
}