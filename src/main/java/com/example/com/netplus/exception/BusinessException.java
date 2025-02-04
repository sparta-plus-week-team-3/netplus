package com.example.com.netplus.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 상위 클래스의 message로 ErrorCode의 기본 메시지 사용
        this.errorCode = errorCode;
    }

    // 상세 메시지를 포함하는 생성자
    public BusinessException(ErrorCode errorCode, String detail) {
        super(detail); // 상위 클래스의 message로 상세 메시지 사용
        this.errorCode = errorCode;
    }
}
