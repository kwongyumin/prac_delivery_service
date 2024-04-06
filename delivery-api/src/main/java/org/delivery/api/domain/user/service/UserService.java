package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.core.user.UserEntity;
import org.delivery.core.user.UserRepository;
import org.delivery.core.user.enums.UserStatus;
import org.service.common.error.ErrorCode;
import org.service.common.error.UserErrorCode;
import org.service.common.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity register(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT_ERROR, "User Entity Null"));
    }

    public UserEntity login(String email, String password) {
        return getUserWithThrow(email, password);
    }

    public UserEntity getUserWithThrow(String email, String password) {
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(Long userId) {
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

}
