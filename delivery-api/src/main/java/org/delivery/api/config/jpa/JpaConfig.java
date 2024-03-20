package org.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration // 빈 등록
@EntityScan(basePackages = "org.delivery.core") // <- 하위 패키지의 entity 스캔 ,
@EnableJpaRepositories(basePackages = "org.delivery.core") // 마찬가지로 repository 스캔
public class JpaConfig {

}
