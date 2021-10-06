package com.bithumb.image.controller;

import com.bithumb.image.common.response.ApiResponse;
import com.bithumb.image.common.response.StatusCode;
import com.bithumb.image.common.response.SuccessCode;
import com.bithumb.image.service.S3Uploader;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Api(tags = {"Image Uploader"})
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*", allowCredentials = "false")
public class UploadController {
    private final S3Uploader s3Uploader;

    @PostMapping("/images/{user-no}/{board-no}")
    public ResponseEntity upload(@RequestParam("images") MultipartFile multipartFile, @PathVariable(value="user-no") long userNo, @PathVariable("board-no") long boardNo) throws
            IOException {
        String url = s3Uploader.upload(multipartFile, userNo , boardNo);// 1,2);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.IMAGE_UPLOAD_SUCCESS.getMessage(),url);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
