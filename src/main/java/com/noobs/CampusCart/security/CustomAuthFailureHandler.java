package com.noobs.CampusCart.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import com.noobs.CampusCart.utils.AppLogger;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        String email = request.getParameter("email"); // from login form
        AppLogger.log.warn("Login failed for email: {}. Reason: {}", email, exception.getMessage());

        // Redirect back to login page with error
        response.sendRedirect("/signin?error");
    }
}
