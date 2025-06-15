package com.rollerspeed.rollerspeed.config;

import com.rollerspeed.rollerspeed.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String redirectUrl;

        switch (usuario.getRol()) {
            case ALUMNO -> redirectUrl = "/alumnos/dashboard/" + usuario.getId();
            case ADMIN -> redirectUrl = "/admin/dashboard";
            case INSTRUCTOR -> redirectUrl = "/instructores/dashboard";
            default -> redirectUrl = "/";
        }

        response.sendRedirect(redirectUrl);
    }
}
