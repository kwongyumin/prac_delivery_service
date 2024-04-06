package org.delivery.api.domain.storemenu.converter;

import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.core.storemenu.StoreMenuEntity;
import org.service.common.annotation.Converter;
import org.service.common.error.ErrorCode;
import org.service.common.exception.ApiException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreMenuEntity.builder()
                            .storeId(request.getStoreId())
                            .name(request.getName())
                            .amount(request.getAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .build();
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    return StoreMenuResponse.builder()
                            .id(entity.getStoreId())
                            .storeId(entity.getStoreId())
                            .name(entity.getName())
                            .amount(entity.getAmount())
                            .status(entity.getStatus())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .likeCount(entity.getLikeCount())
                            .sequence(entity.getSequence())
                            .build();
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

    public List<StoreMenuResponse> toResponse (List<StoreMenuEntity> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
