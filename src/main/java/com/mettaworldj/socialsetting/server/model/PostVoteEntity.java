package com.mettaworldj.socialsetting.server.model;

import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@IdClass(PostVoteEntity.PostVoteEntityId.class)
public class PostVoteEntity {


    @Id
    private Long subSettingId;

    @Id
    private Long postId;

    @Id
    private Long userId;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    private VoteType voteType;

    public enum VoteType {
        UPVOTE(1), DOWNVOTE(-1),
        ;

        private int direction;

        VoteType(int direction) {
        }

        public static VoteType lookup(Integer direction) {
            return Arrays.stream(VoteType.values())
                    .filter(value -> value.getDirection().equals(direction))
                    .findAny()
                    .orElseThrow(() -> new SocialSettingException("Vote not found", HttpStatus.NOT_FOUND));
        }

        public Integer getDirection() {
            return direction;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostVoteEntityId implements Serializable {

        private Long subSettingId;

        private Long postId;

        private Long userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PostVoteEntityId that = (PostVoteEntityId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(postId, that.postId) && Objects.equals(subSettingId, that.subSettingId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, postId, subSettingId);
        }

    }
}
