package org.service.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token 의 경우 2000번대 에러코드를 사용한다.
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs {


    INVALID_TOKEN(400 , 2000 , "유효하지 않는 토큰"),
    EXPIRED_TOKEN(400 , 2001 , "만료된 토큰"),
    TOKEN_EXCEPTION(400, 2002, "토큰과 관련하여 알 수 없는 예외가 발생했습니다."),
    AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "헤더에 인증 토큰이 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
