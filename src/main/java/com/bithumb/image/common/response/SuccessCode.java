package com.bithumb.image.common.response;

public enum SuccessCode {
    IMAGE_UPLOAD_SUCCESS("IMAGE UPLOAD SUCCESS");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
