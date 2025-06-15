package com.rollerspeed.rollerspeed.config;

import com.rollerspeed.rollerspeed.model.Rol;
import com.rollerspeed.rollerspeed.model.Usuario;
import com.rollerspeed.rollerspeed.repository.UsuarioRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            Dotenv dotenv = Dotenv.load(); // ✅ Cargar .env

            String email = dotenv.get("ADMIN_EMAIL");
            String nombre = dotenv.get("ADMIN_NOMBRE");
            String password = dotenv.get("ADMIN_PASSWORD");
            String telefono = dotenv.get("ADMIN_TELEFONO");

            if (email == null || email.isBlank()) {
                System.out.println("❌ ADMIN_EMAIL no definido");
                return;
            }

            if (usuarioRepository.findByEmail(email).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre(nombre);
                admin.setEmail(email);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setTelefono(telefono);
                admin.setRol(Rol.ADMIN);
                usuarioRepository.save(admin);
                System.out.println("✅ Usuario administrador creado: " + email);
            } else {
                System.out.println("ℹ️ Usuario administrador ya existe");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creando admin: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
