package org.delivery.storeadmin.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.core.storemenu.StoreMenuEntity;
import org.delivery.core.storemenu.StoreMenuRepository;
import org.delivery.core.storemenu.enums.StoreMenuStatus;
import org.service.common.error.ErrorCode;
import org.service.common.exception.ApiException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    // TODO 테스트 이후 관련 예외 메시지 생성
    public StoreMenuEntity getStoreMenuWithThrow(Long storeMenuId) {
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(storeMenuId, StoreMenuStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

}
