package org.delivery.storeadmin.domain.storeuser.service;

import lombok.RequiredArgsConstructor;
import org.delivery.core.storeuser.StoreUserEntity;
import org.delivery.core.storeuser.StoreUserRepository;
import org.delivery.core.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public StoreUserEntity register(StoreUserEntity storeUserEntity) {

        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        storeUserEntity.setRegisteredAt(LocalDateTime.now());

        return storeUserRepository.save(storeUserEntity);
    }

    public Optional<StoreUserEntity> getRegisterUser(String email) {
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email , StoreUserStatus.REGISTERED);
    }

}
