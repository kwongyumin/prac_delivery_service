package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.core.store.enums.StoreCategory;
import org.service.common.api.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<List<StoreResponse>> search(@RequestParam(required = false)StoreCategory storeCategory) {
        var response = storeBusiness.searchCategory(storeCategory);
        return Api.OK(response);
    }


}
