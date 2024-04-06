package org.delivery.api.domain.userordermenu.service;


import lombok.RequiredArgsConstructor;
import org.delivery.core.userordermenu.UserOrderMenuEntity;
import org.delivery.core.userordermenu.UserOrderMenuRepository;
import org.delivery.core.userordermenu.enums.UserOrderMenuStatus;
import org.service.common.error.ErrorCode;
import org.service.common.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }

    public UserOrderMenuEntity order(UserOrderMenuEntity userOrderMenuEntity) {
        return Optional.ofNullable(userOrderMenuEntity)
                .map(it -> {
                    it.setStatus(UserOrderMenuStatus.REGISTERED);
                    return userOrderMenuRepository.save(it);
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR));
    }

}
