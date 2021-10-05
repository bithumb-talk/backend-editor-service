    package com.bithumb.image.common.response;

public enum ErrorCode {
    UPLOAD_FAIL("S3 UPLOAD FAIL");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
