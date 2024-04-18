package org.delivery.storeadmin.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.core.userorder.UserOrderEntity;
import org.delivery.core.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public Optional<UserOrderEntity> getUserOrder(Long userOrderId) {
        return userOrderRepository.findById(userOrderId);
    }

}
