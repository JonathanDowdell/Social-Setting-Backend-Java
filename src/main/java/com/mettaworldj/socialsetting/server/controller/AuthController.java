package com.mettaworldj.socialsetting.server.controller;

import com.mettaworldj.socialsetting.server.dto.auth.request.SignUpRequestDto;
import com.mettaworldj.socialsetting.server.dto.auth.request.VerifyCodeRequestDto;
import com.mettaworldj.socialsetting.server.dto.auth.response.AuthenticationResponseDto;
import com.mettaworldj.socialsetting.server.service.auth.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        authService.signUp(signUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @PostMapping("/token/verify/code")
    public ResponseEntity<AuthenticationResponseDto> verifyCode(@RequestBody VerifyCodeRequestDto verifyCodeRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.verifyAccount(verifyCodeRequestDto.getCode()));
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> usernames() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.usernames());
    }

}
