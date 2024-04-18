package org.delivery.storeadmin.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.core.userordermenu.UserOrderMenuEntity;
import org.delivery.core.userordermenu.UserOrderMenuRepository;
import org.delivery.core.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UIserOrderMenuService {


    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenuList(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);

    }

}
