package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.core.store.StoreRepository;
import org.delivery.core.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storeuser.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;

    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO 테스트 이후 NPE 체크 추가
        var storeUserEntity = storeUserService.getRegisterUser(username);
        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);

        return storeUserEntity.map(
                it -> UserSession.builder()
                        .userId(it.getId())
                        .email(it.getEmail())
                        .password(it.getPassword())
                        .status(it.getStatus())
                        .role(it.getRole())
                        .registeredAt(it.getRegisteredAt())
                        .lastLoginAt(it.getLastLoginAt())
                        .unregisteredAt(it.getUnregisteredAt())
                        .storeId(storeEntity.get().getId())
                        .storeName(storeEntity.get().getName())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
