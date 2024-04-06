package org.delivery.api.domain.userordermenu.converter;

import org.delivery.core.storemenu.StoreMenuEntity;
import org.delivery.core.userorder.UserOrderEntity;
import org.delivery.core.userordermenu.UserOrderMenuEntity;
import org.service.common.annotation.Converter;

@Converter
public class UserOrderMenuConverter {

    public UserOrderMenuEntity toEntity (UserOrderEntity userOrderEntity, StoreMenuEntity storeMenuEntity) {
        return UserOrderMenuEntity.builder()
                .userOrderId(userOrderEntity.getId())
                .storeMenuId(storeMenuEntity.getId())
                .build();
    }

}
