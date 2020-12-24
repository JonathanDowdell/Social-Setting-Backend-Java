package com.mettaworldj.socialsetting.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@IdClass(VerificationTokenEntity.VerificationTokenEntityId.class)
public class VerificationTokenEntity {

    @Id
    private String verificationTokenId;

    @Id
    private int code;

    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class VerificationTokenEntityId implements Serializable {
        private String verificationTokenId;

        private int code;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VerificationTokenEntityId that = (VerificationTokenEntityId) o;
            return code == that.code && Objects.equals(verificationTokenId, that.verificationTokenId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(verificationTokenId, code);
        }
    }

}
