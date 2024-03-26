package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.core.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;


    /**
     *  1. user Entity userId 추출
     *  2. access , refresh token 발행
     *  3. converter -> token response 변경
     */
    public TokenResponse issueToken(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(UserEntity::getId)
                .map(userId -> {
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    public Long validationAccessToken(String accessToken) {
        return tokenService.validationToken(accessToken);
    }

}
