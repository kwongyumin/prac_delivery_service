package org.delivery.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.core.store.StoreRepository;
import org.delivery.core.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.service.common.annotation.Business;
import org.service.common.error.ErrorCode;
import org.service.common.exception.ApiException;

@RequiredArgsConstructor
@Business
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;

    private final StoreUserService storeUserService;

    // TODO SERVICE 로 변경 필요
    private final StoreRepository storeRepository;

    public StoreUserResponse register(StoreUserRegisterRequest request) {

        var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));

        var entity = storeUserConverter.toEntity(request, storeEntity);
        var newEntity = storeUserService.register(entity);
        var response = storeUserConverter.toResponse(newEntity, storeEntity);
        return response;
    }

}
