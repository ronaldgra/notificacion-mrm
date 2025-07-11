package com.bancolombia.mrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtil {
    public static String encodeImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            // Loggear el error
            System.err.println("Error al leer la imagen: " + imagePath);
            return "";
        }
    }
}
