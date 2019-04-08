package com.example.granny.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadImage(MultipartFile multipartFile, String imgId) throws IOException;

    void deleteImage(String imgId) throws IOException;

}
