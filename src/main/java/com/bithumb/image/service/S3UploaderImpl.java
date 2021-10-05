package com.bithumb.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3UploaderImpl implements S3Uploader {

//    private final AmazonS3Client amazonS3Client;
    private final AmazonS3Client amazonS3ClientPublic;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile, long userId, long boardNo) throws IOException {//String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return uploadImg(uploadFile , userId, boardNo);
    }
    public String uploadImg(File uploadFile,long userId, long boardNo) {
        String fileName = "user"+String.valueOf(userId)+"/board"+String.valueOf(boardNo)+"/"+String.valueOf(LocalDateTime.now()); //+ uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    public String putS3(File uploadFile, String fileName) {
        amazonS3ClientPublic.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3ClientPublic.getUrl(bucket, fileName).toString();
    }
    public void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            //System.out.println("File delete success");
            //    log.info("File delete success");
            return;
        }
        System.out.println("File delete fail");
        //log.info("File delete fail");
    }

    public Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());

        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
