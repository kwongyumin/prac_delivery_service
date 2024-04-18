package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.service.common.annotation.Business;
import org.service.common.error.UserErrorCode;
import org.service.common.exception.ApiException;
import org.service.common.message.model.UserOrderMessage;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;

    private final SseConnectionPool sseConnectionPool;

    /**
     * 주문 -> 주문 내역-> 스토어 찾기-> 연결된 세션 찾아서 push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {

        // TODO 테스트 후 주문 내역에 대한 예외 메시지 생성
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
                () -> new ApiException(UserErrorCode.USER_NOT_FOUND)
        );
        // userOrderEntity

        // userOrderMenu

        // menu -> storeMenu

        // response

        // push


        // 연결된 커넥션
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 주문 메뉴, 가격, 상태

        // 사용자에게 push
        // userConnection.sendMessage();

    }


}
