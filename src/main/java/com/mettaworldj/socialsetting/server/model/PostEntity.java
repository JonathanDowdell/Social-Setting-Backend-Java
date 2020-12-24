package com.mettaworldj.socialsetting.server.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(PostEntity.PostEntityId.class)
public class PostEntity {

    @Id
    private Long subSettingId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @NotNull(message = "Post name cannot be null")
    @NotBlank(message = "Post name cannot be empty")
    private String postName;

    @Nullable
    @Lob
    private String description;

    @Nullable
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubSettingEntity subSettingEntity;

    private int voteCount = 0;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostEntityId implements Serializable {

        private Long subSettingId;

        private Long postId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PostEntityId that = (PostEntityId) o;
            return Objects.equals(subSettingId, that.subSettingId) && Objects.equals(postId, that.postId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subSettingId, postId);
        }
    }
}
