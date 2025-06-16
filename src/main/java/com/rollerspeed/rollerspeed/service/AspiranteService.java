package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.repository.AspiranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AspiranteService {

    private final AspiranteRepository aspiranteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir:C:/Temp/uploads}")
    private String uploadDir;

    @Autowired
    public AspiranteService(AspiranteRepository aspiranteRepository, PasswordEncoder passwordEncoder) {
        this.aspiranteRepository = aspiranteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Guardar nuevo aspirante con archivo de imagen
    public Aspirante guardarAspirante(Aspirante aspirante, MultipartFile archivoFoto) throws IOException {
        // Codificar contraseña
        aspirante.setPassword(passwordEncoder.encode(aspirante.getPassword()));

        // Si el archivo no está vacío, lo guardamos
        if (archivoFoto != null && !archivoFoto.isEmpty()) {
            String nombreArchivo = guardarArchivo(archivoFoto);
            aspirante.setFotoUrl(nombreArchivo);
        }

        return aspiranteRepository.save(aspirante);
    }

    public List<Aspirante> listarAspirantes() {
        return aspiranteRepository.findAll();
    }

    public Optional<Aspirante> obtenerAspirantePorId(Long id) {
        return aspiranteRepository.findById(id);
    }

    public void eliminarAspirante(Long id) {
        aspiranteRepository.deleteById(id);
    }

    // Método para guardar el archivo en el sistema de archivos
    private String guardarArchivo(MultipartFile archivo) throws IOException {
        String extension = StringUtils.getFilenameExtension(archivo.getOriginalFilename());
        String nombreArchivo = UUID.randomUUID() + "." + extension;

        Path directorio = Paths.get(uploadDir);
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }

        Path rutaArchivo = directorio.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        return nombreArchivo;
    }
}
