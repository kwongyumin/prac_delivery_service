package org.delivery.core.userorder;

import org.delivery.core.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Long> {


    // 특정 유저의 모든 주문
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId , UserOrderStatus status);

    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId , List<UserOrderStatus> status);

    // 특정 주문에 대한 정보
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);
    // 특정 주문에 대한 정보 (상태값 x)
    Optional<UserOrderEntity> findAllByIdAndUserId(Long id,Long userId);

}
