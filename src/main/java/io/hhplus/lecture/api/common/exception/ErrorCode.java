package io.hhplus.lecture.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 값이 유효한지 확인하세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "해당 특강을 조회할 수 없습니다."),

    LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 특강을 조회할 수 없습니다."),

    LECTURE_CAPACITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 특강의 정원 정보를 찾을 수 없습니다."),

    ALREADY_REGISTERED(HttpStatus.CONFLICT, "이미 해당 특강에 신청되었습니다."),

    FULL_CAPACITY(HttpStatus.CONFLICT, "해당 특강 신청은 정원 초과되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
