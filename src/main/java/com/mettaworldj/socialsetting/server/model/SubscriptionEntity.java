package com.mettaworldj.socialsetting.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(SubscriptionEntity.SubscriptionEntityId.class)
public class SubscriptionEntity {

    @Id
    private Long subSettingId;

    @Id
    private Long userId;

    @ManyToOne
    private SubSettingEntity subSettingEntity;

    @ManyToOne
    private UserEntity userEntity;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubscriptionEntityId implements Serializable {

        private Long subSettingId;

        private Long userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SubscriptionEntityId that = (SubscriptionEntityId) o;
            return Objects.equals(subSettingId, that.subSettingId) && Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subSettingId, userId);
        }
    }

}
