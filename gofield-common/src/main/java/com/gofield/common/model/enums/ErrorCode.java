package com.gofield.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 400 Bad Request (잘못된 요청)
     */
    E400_INVALID_EXCEPTION("I4000", 400, "파라미터 오류"),
    E400_INVALID_FILE_FORMAT_EXCEPTION("I4002", 400, "허용되지 않은 파일입니다"),
    E400_INVALID_GOOGLE_CODE_EXCEPTION("I4003", 400, "잘못된 구글 코드 입니다"),
    E400_INVALID_CLASS_NUMBER_EXCEPTION("I4001", 400, "잘못된 아이디입니다."),
    E400_INVALID_APPLE_TOKEN_EXCEPTION("I4004", 400, "잘못된 애플 토큰입니다."),
    E400_INVALID_AUTH_TOKEN("I4005", 400, "유효하지 않는 토큰입니다."),
    E400_INVALID_MISSING_AUTH_TOKEN_PARAMETER("I4006", 400, "필수 파라미터가 요청되지 않았습니다."),
    /**
     * 401 UnAuthorized (토큰 만료)
     */
    E401_UNAUTHORIZED("U4100", 401 , "엑세스 토큰 만료"),


    /**
     * 403 Forbidden (권한이 없을 경우)
     */
    E403_FORBIDDEN_EXCEPTION("F4300", 403, "해당 권한이 없습니다"),


    /**
     * 404 Not Found (존재하지 않는 리소스)
     */
    E404_NOT_FOUND_EXCEPTION("N4400", 403, "허용되지 않는 HTTP 메소드입니다."),


    /**
     * 405 Method Not Allowed
     */
    E405_METHOD_NOT_ALLOWED_EXCEPTION("M4500", 405, "허용되지 않는 HTTP 메소드입니다."),


    /**
     * 409 Conflict (중복되는 리소스)
     */
    E409_CONFLICT_EXCEPTION("C4900", 409, "이미 존재하는 리소스입니다."),


    /**
     * 415 Unsupported media type (지원하지 않는 미디어 타입)
     */
    E415_UNSUPPORTED_MEDIA_TYPE("U4500", 415, "지원하지 않는 미디어 타입입니다."),


    /**
     * 470 Convert (데이터 변환 오류)
     */
    E470_CONVERT_EXCEPTION("X4700", 470, "컨버트 오류"),

    /**
     * 499 Internal Rule (내부 예외 규칙)
     */
    E499_INTERNAL_RULE("R4990", 499, "내부 예외"),

    /**
     * 500 Internal Server Exception (서버 내부 에러)
     */
    E500_INTERNAL_SERVER("Z5000",  500, "예상치 못한 에러가 발생하였습니다. 잠시 후 다시 시도해주세요!"),
    E500_INTERNAL_SERVER_ENCRYPT("Z5001",  500, "암호화 오류"),
    E500_INTERNAL_SERVER_DECRYPT("Z5002",  500, "복호화 오류"),
    E500_INTERNAL_CONVERT_ENUM_CODE("Z5004",  500, "컨버트 오류"),


    /**
     * 502 Bad Gateway
     */
    E502_BAD_GATEWAY("B5200",  502, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요!"),


    /**
     * 503 Service UnAvailable
     */
    E503_SERVICE_UNAVAILABLE("B5300", 503,  "사용할 수 없는 기능입니다");


    private final String code;
    private final int statusCode;
    private final String message;

    public int getStatusCode() {
        return this.statusCode;
    }
    public String getCode() { return this.code; }
    public String getMessage() { return this.message; }

}
