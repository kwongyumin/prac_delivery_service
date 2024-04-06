package org.delivery.storeadmin.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.service.common.api.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    public Api<StoreUserResponse> register(@Valid @RequestBody StoreUserRegisterRequest request) {
        return null;
    }

}
