package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.core.storemenu.StoreMenuEntity;
import org.delivery.core.storemenu.StoreMenuRepository;
import org.delivery.core.storemenu.enums.StoreMenuStatus;
import org.service.common.error.ErrorCode;
import org.service.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id) {
        var entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id,StoreMenuStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED);
    }


    public StoreMenuEntity register(StoreMenuEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    entity.setStatus(StoreMenuStatus.REGISTERED);
                    return storeMenuRepository.save(it);
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

}
