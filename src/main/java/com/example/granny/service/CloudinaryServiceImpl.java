package com.example.granny.service;

import com.cloudinary.Cloudinary;
import com.example.granny.constants.GlobalConstants;
import com.example.granny.service.api.CloudinaryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile, String imgId) throws IOException {
        File file = File
                .createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        Map<String, String> map = new HashMap<>();
        map.put("public_id", imgId);

        return cloudinary.uploader()
                .upload(file, map)
                .get("url").toString();
    }

    @Override
    public void deleteImage(String imgId) throws IOException {
        cloudinary.uploader()
                .destroy(imgId, new HashMap<>());
    }
}
