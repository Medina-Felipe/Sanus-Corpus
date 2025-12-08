package com.backendpill.shared.application;

import com.backendpill.shared.domain.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    // En el futuro, esto se configuraría en application.yml
    // private final Path rootLocation = Paths.get("uploads");

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("No se puede guardar un archivo vacío");
        }

        // 1. Limpiar nombre del archivo
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        // 2. Generar nombre único para evitar colisiones (ej: imagen.png -> uuid-imagen.png)
        String fileName = UUID.randomUUID().toString() + "-" + originalFilename;

        try {
            // AQUÍ IRÍA LA LÓGICA REAL DE GUARDADO (Filesystem o S3)
            // Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));

            // Simulamos que devolvemos la URL pública
            return "https://cdn.backendpill.com/images/" + fileName;

        } catch (Exception e) {
            throw new BusinessException("Fallo al almacenar el archivo " + fileName, e);
        }
    }

    public void deleteFile(String fileUrl) {
        // Lógica para borrar archivo si se elimina el producto
        System.out.println("Archivo eliminado simulado: " + fileUrl);
    }
}