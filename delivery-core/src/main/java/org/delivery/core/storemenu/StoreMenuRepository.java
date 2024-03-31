package org.delivery.core.storemenu;

import org.delivery.core.storemenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    // 유효한 메뉴 체크
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id , StoreMenuStatus status);

    // 특정 가게 메뉴
    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId , StoreMenuStatus status);

}
