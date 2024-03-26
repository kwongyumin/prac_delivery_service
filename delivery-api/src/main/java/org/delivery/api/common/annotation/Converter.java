package org.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 커스텀 어노테이션을 통해 역할을 명시적으로 처리한다.
 * DTO <-> Entity 변환 등의 역할을 수행하는 비즈니스를 명시적으로 나타내기 위해
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service // spring -> service -> business 빈 처리
public @interface Converter {

    @AliasFor(annotation = Service.class)
    String value() default "";

}
