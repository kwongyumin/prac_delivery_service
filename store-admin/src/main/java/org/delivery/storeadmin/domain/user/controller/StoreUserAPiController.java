package org.delivery.storeadmin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.service.common.api.Api;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-user")
public class StoreUserAPiController {

    private final StoreUserConverter storeUserConverter;


    @GetMapping("/me")
    public Api<StoreUserResponse> me (@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {
        var response = storeUserConverter.toResponse(userSession);
        return Api.OK(response);
    }




}
