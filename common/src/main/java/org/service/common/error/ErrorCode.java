package org.service.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{

    OK(200 , 200 , "success") ,
    BAD_REQUEST(400,400, "api bad request") ,
    SERVER_ERROR(500,500,"server error") ,
    NULL_POINT_ERROR(500,512,"null point exception")

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
