package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.service.common.annotation.Business;
import org.service.common.error.UserErrorCode;
import org.service.common.exception.ApiException;
import org.service.common.message.model.UserOrderMessage;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;

    private final UserOrderConverter userOrderConverter;

    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;

    private final StoreMenuConverter storeMenuConverter;

    /**
     * 주문 -> 주문 내역-> 스토어 찾기-> 연결된 세션 찾아서 push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {

        // TODO 테스트 후 주문 내역에 대한 예외 메시지 생성
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
                () -> new ApiException(UserErrorCode.USER_NOT_FOUND)
        );

        // userOrderMenu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // userOrderMenu -> storeMenu
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                })
                .map(storeMenuEntity -> {
                    return storeMenuConverter.toResponse(storeMenuEntity);
                }).collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response push
        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();

        // 연결된 커넥션
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 push
        userConnection.sendMessage(push);

    }


}
