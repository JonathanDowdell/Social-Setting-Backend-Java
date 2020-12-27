package com.mettaworldj.socialsetting.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(CommentEntity.CommentEntityId.class)
@Builder
public class CommentEntity {

    @Id
    private Long subSettingId;

    @Id
    private Long postId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @NotBlank
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    private PostEntity postEntity;

    private Instant createdDate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentEntityId implements Serializable {
        private Long subSettingId;
        private Long postId;
        private Long commentId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CommentEntityId that = (CommentEntityId) o;
            return Objects.equals(subSettingId, that.subSettingId) && Objects.equals(postId, that.postId) && Objects.equals(commentId, that.commentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subSettingId, postId, commentId);
        }
    }

}
