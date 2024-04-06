package org.delivery.storeadmin.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.core.storeuser.enums.StoreUserRole;
import org.delivery.core.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreUserResponse {


    private UserResponse user;

    private StoreResponse store;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class UserResponse{

        private Long id; // 사용자

        private String email;

        private String password;

        private StoreUserStatus status;

        private StoreUserRole role;

        private LocalDateTime registeredAt;

        private LocalDateTime unregisteredAt;

        private LocalDateTime lastLoginAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class StoreResponse{

        private Long storeId; // 가맹점

        private String name;
    }


}
