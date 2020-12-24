package com.mettaworldj.socialsetting.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mettaworldj.socialsetting.server.dto.auth.request.SignInRequestDto;
import com.mettaworldj.socialsetting.server.dto.auth.request.SignUpRequestDto;
import com.mettaworldj.socialsetting.server.dto.auth.response.AuthenticationResponseDto;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.UserRepository;
import com.mettaworldj.socialsetting.server.service.refresh.IRefreshTokenService;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.mettaworldj.socialsetting.server.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final IRefreshTokenService refreshTokenRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, IRefreshTokenService refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final SignInRequestDto credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), SignInRequestDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername().toLowerCase(), credentials.getPassword(), new ArrayList<>());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final String username = ((User) authResult.getPrincipal()).getUsername();

        final UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new SocialSettingException("User not found", HttpStatus.NOT_FOUND));

        final Date expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        final String token = JWT.create()
                .withSubject(username)
                .withClaim("publicId", userEntity.getPublicId())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        final AuthenticationResponseDto.AuthExpireAt authExpireAt = AuthenticationResponseDto.AuthExpireAt.builder()
                .nano(expiresAt.toInstant().getNano()).epochSecond(expiresAt.toInstant().getEpochSecond()).build();

        final AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .expiresAt(authExpireAt)
                .profileName(userEntity.getProfileName())
                .publicId(userEntity.getPublicId())
                .refreshToken(refreshTokenRepository.generateRefreshToken().getToken())
                .username(userEntity.getUsername())
                .build();

        final String acceptType = request.getHeader("Accept") == null ? "application/json" : request.getHeader("Accept");
        response.setContentType(acceptType);
        response.setCharacterEncoding("UTF-8");
        final String responseData = new ObjectMapper().writeValueAsString(authenticationResponseDto);
        response.getWriter().write(toJSONorXML(acceptType, responseData));
        response.getWriter().flush();
    }

    private String toJSONorXML(String contentType, String data) {
        if (contentType.equals("application/xml")) {
            JSONObject jsonObject = new JSONObject(data);
            return "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<root>" + XML.toString(jsonObject) + "</root>";
        } else {
            return data;
        }
    }
}
