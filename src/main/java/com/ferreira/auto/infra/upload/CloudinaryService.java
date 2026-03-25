package com.ferreira.auto.infra.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String upload(MultipartFile file) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            Object url = result.get("secure_url");
            if (url != null) {
                return url.toString();
            }
            return (String) result.get("url");
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
}
