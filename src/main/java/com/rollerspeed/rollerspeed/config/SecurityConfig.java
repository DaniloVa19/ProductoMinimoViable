package com.rollerspeed.rollerspeed.config;

import com.rollerspeed.rollerspeed.service.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioServiceImpl usuarioService;
    private final CustomSuccessHandler successHandler;

    public SecurityConfig(UsuarioServiceImpl usuarioService, CustomSuccessHandler successHandler) {
        this.usuarioService = usuarioService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers(
                        "/", "/home/**", "/register", "/css/**", "/js/**",
                        "/doc", "/swagger-ui.html", "/swagger-ui.html/**", "/swagger-ui/**",
                        "/aspirantes/nuevo", "/login", "/error", "/images/**",
                        "/uploads/**", // ✅ Permitir acceso público a imágenes
                        "/mision", "/vision", "/servicios", "/eventos", "/valores"
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/aspirantes/guardar").permitAll()

                // Rutas restringidas por rol
                .requestMatchers("/alumnos/dashboard/**").hasRole("ALUMNO")
                .requestMatchers(
                        "/metodos_pago/nuevo",
                        "/clases/nueva",
                        "/clases/lista",
                        "/instructores/nuevo"
                ).hasAnyRole("ADMIN", "INSTRUCTOR")

                // Todas las demás requieren autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler) // Redirección personalizada por rol
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // Redirige a la raíz después de cerrar sesión
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .userDetailsService(usuarioService)
            .csrf(csrf -> csrf.disable()); // ⚠️ Desactivado solo en desarrollo

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioService;
    }
}
