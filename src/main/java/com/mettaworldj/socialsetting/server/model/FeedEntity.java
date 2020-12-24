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
@IdClass(FeedEntity.FeedEntityId.class)
public class FeedEntity {

    @Id
    private Long userId;

    @Id
    private Long postId;

    @Id
    private Long subSettingId;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private PostEntity postEntity;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeedEntityId implements Serializable {
        private Long userId;
        private Long postId;
        private Long subSettingId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FeedEntityId that = (FeedEntityId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(postId, that.postId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, postId);
        }
    }
}
