package org.delivery.core.account;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.core.BaseEntity;

@SuperBuilder // 부모로부터 상속받은 필드도 포함 시킨다.
@Data
@EqualsAndHashCode(callSuper = true) // 객체의 동등성 비교, 옵션 -> 부모의 값까지 같이 비교
@Entity
@Table(name = "account")
@NoArgsConstructor
public class AccountEntity extends BaseEntity {
}
