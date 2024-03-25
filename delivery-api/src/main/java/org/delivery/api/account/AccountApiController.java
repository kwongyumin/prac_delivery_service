package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.core.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountRepository accountRepository;
    // 스프링 -> 하위 패키지의 빈으로 등록시킬 어노테이션을 찾지만 , core 와는 패키지가 상이하여 찾지못함.
    // config 클래스파일을 생성하여 빈을 스캔 한다.

    @GetMapping("/me")
    public Api<AccountMeResponse> me() {
        var response = AccountMeResponse.builder()
                .name("테스터 ")
                .email("eamil@tester.com")
                .registeredAt(LocalDateTime.now())
                .build();

        return Api.OK(response);

    }
}
