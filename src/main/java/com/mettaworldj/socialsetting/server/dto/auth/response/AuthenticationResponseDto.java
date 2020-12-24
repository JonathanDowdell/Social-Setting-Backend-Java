package com.mettaworldj.socialsetting.server.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {
    private String publicId;
    private String authenticationToken;
    private String refreshToken;
    private AuthExpireAt expiresAt;
    private String username;
    private String profileName;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AuthExpireAt {
        private long nano;
        private long epochSecond;
    }
}