package org.service.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 커스텀 어노테이션을 통해 역할을 명시적으로 처리한다.
 * -> 도메인에 종속적이지 않고 , 외부와 연결되어 처리되어지는 비즈니스를 명시적으로 분리하기 위함
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service // spring -> service -> business 빈 처리
public @interface Business {

    @AliasFor(annotation = Service.class)
    String value() default "";

}
