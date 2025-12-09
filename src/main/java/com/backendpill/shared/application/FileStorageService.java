package com.backendpill.shared.application;

import com.backendpill.shared.domain.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Servicio encargado de la gestión de archivos (imágenes, documentos).
 * <p>
 * Abstrae la lógica de almacenamiento físico (sistema de archivos local, AWS S3, Google Cloud Storage).
 * Actualmente implementa una simulación para entornos de desarrollo.
 */
@Service
public class FileStorageService {

    // private final Path rootLocation = Paths.get("uploads");

    /**
     * Almacena un archivo recibido y retorna su URL de acceso público.
     *
     * @param file El archivo binario recibido del cliente.
     * @return La URL pública (CDN o local) del archivo guardado.
     * @throws BusinessException Si el archivo está vacío o ocurre un error de I/O.
     */
    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("No se puede guardar un archivo vacío");
        }

        // 1. Sanitización: Limpia el nombre para evitar ataques de "Path Traversal" (ej: ../../etc/passwd)
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        // 2. Unicidad: Previene colisiones de nombres usando UUID
        String fileName = UUID.randomUUID().toString() + "-" + originalFilename;

        try {
            // TODO: Implementar lógica de persistencia real (Files.copy o S3 SDK)
            // Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));

            // Retorno simulado de URL de CDN
            return "https://cdn.backendpill.com/images/" + fileName;

        } catch (Exception e) {
            // Envolvemos excepciones chequeadas (IOException) en RuntimeException de negocio
            throw new BusinessException("Fallo al almacenar el archivo " + fileName, e);
        }
    }

    /**
     * Elimina un archivo del almacenamiento.
     *
     * @param fileUrl La URL o identificador del archivo a borrar.
     */
    public void deleteFile(String fileUrl) {
        // Lógica de limpieza (Soft delete o Hard delete en S3/Disco)
        System.out.println("Archivo eliminado simulado: " + fileUrl);
    }
}