package com.bithumb.image.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public interface S3Uploader {
    String upload(MultipartFile multipartFile, long userId, long boardNo) throws IOException;
    String uploadImg(File uploadFile, long userId, long boardNo);
    String putS3(File uploadFile, String fileName);
    void removeNewFile(File targetFile);
    Optional<File> convert(MultipartFile file) throws IOException;
}
