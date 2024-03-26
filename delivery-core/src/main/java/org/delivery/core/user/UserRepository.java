package org.delivery.core.user;

import org.delivery.core.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    // select * from dalivery.user where id = '' and status = '' order by id desc limit 1;
    Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long userId , UserStatus userStatus);

    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus userStatus);
}
