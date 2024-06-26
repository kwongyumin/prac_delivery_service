package org.delivery.api.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.delivery.core.store.StoreEntity;
import org.delivery.core.store.StoreRepository;
import org.delivery.core.store.enums.StoreCategory;
import org.delivery.core.store.enums.StoreStatus;
import org.service.common.error.ErrorCode;
import org.service.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    // 유효한 스토어 가져오기
    public StoreEntity getStoreWithThorw(Long id) {
        return storeRepository.findFirstByIdAndStatusOrderByIdDesc(id , StoreStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                .map(it -> {

                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    // TODO 등록일시 추가

                    return storeRepository.save(it);
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    // 카테고리로 스토어 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory) {
        var list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED ,
                storeCategory
        );
        return list;
    }
    // 전체 스토어

    public List<StoreEntity> registerStore() {
        var list = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
        return list;
    }

}


