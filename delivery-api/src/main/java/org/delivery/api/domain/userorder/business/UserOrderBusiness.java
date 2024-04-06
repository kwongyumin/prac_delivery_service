package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.service.common.annotation.Business;

import java.util.List;
@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderConverter userOrderConverter;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderService userOrderService;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreService storeService;

    // 1. 사용자 , 메뉴 id
    // 2. 사용자의 주문
    // 3. 사용자 메뉴 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        var storeMeneEntityList = body.getStoreMenuIdList()
                .stream().map(storeMenuService::getStoreMenuWithThrow).toList();

        var userOrderEntity = userOrderConverter.toEntity(user,storeMeneEntityList);

        //주문
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        //매핑
        var userOrderMenuEntityList = storeMeneEntityList.stream()
                .map(it -> {
                    // 메뉴 + 주문
                    return userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                    }).toList();

        // 주문 내역 기뢱 남기기 ,
        userOrderMenuEntityList.forEach(userOrderMenuService::order);

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }
    // TODO 리팩토링

    public List<UserOrderDetailResponse> current(User user) {
        // 사용자 정보를 통해 주문내역을 가져온다.
        var userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1 건씩 처리
        return userOrderEntityList.stream().map(it -> {

            // 사용자가 주문한 메뉴
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity ->{
                        return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            }).toList();

            // 사용자가 주문한 스토어
            var storeEntity = storeService.getStoreWithThorw(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponses(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).toList();
    }

    public List<UserOrderDetailResponse> history(User user) {
        // 사용자 정보를 통해 주문내역을 가져온다.
        var userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1 건씩 처리
        return userOrderEntityList.stream().map(it -> {

            // 사용자가 주문한 메뉴
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity ->{
                return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            }).toList();

            // 사용자가 주문한 스토어
            var storeEntity = storeService.getStoreWithThorw(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponses(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).toList();
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        // 사용자의 특정 주문
        var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());
        // 사용자가 주문한 메뉴
        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity ->{
            return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
        }).toList();

        // 사용자가 주문한 스토어
        var storeEntity = storeService.getStoreWithThorw(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponses(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();

    }
}
