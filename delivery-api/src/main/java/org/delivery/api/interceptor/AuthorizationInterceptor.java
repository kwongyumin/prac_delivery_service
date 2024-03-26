package org.delivery.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            log.info("Authorization Interceptor Url : {}" , request.getRequestURI());

            // WEB, chrome 의 경우 GET,POST api 요청 전 OPTIONS 이라는 API 를 요청하여 해당 메소드를 지원하는지 체크함  ->  pass
            if (HttpMethod.OPTIONS.matches(request.getMethod())) {
                return true;
            }
            // js, html , png resource 를 요청하는 경우 -> pass
            if (handler instanceof ResourceHttpRequestHandler) {
                return true;
            }
            var accessToken = request.getHeader("authorization-token");
            if (accessToken == null) {
                throw  new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
            }

            var userId = tokenBusiness.validationAccessToken(accessToken);

            if (userId != null) {
                // 한 번의 요청 -> 생성 , 글로벌 저장소인 스레드 로컬에 저장 ,
                var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
                // 세션 단위 혹은 스코프 단위로 값을 저장할 것인지 옵션 설정
                requestContext.setAttribute("userId",userId, RequestAttributes.SCOPE_REQUEST);
                return true;
            }

            throw new ApiException(ErrorCode.BAD_REQUEST, "인증에 실패하였습니다");
    }
}
