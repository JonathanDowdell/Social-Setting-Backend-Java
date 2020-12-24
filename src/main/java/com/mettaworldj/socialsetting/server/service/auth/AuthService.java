package com.mettaworldj.socialsetting.server.service.auth;

import com.mettaworldj.socialsetting.server.dto.auth.request.SignUpRequestDto;
import com.mettaworldj.socialsetting.server.dto.auth.response.AuthenticationResponseDto;
import com.mettaworldj.socialsetting.server.dto.general.NotificationEmail;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import com.mettaworldj.socialsetting.server.model.SubscriptionEntity;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.model.VerificationTokenEntity;
import com.mettaworldj.socialsetting.server.repository.SubSettingRepository;
import com.mettaworldj.socialsetting.server.repository.SubscriptionRepository;
import com.mettaworldj.socialsetting.server.repository.UserRepository;
import com.mettaworldj.socialsetting.server.repository.VerificationTokenRepository;
import com.mettaworldj.socialsetting.server.service.mail.IMailService;
import com.mettaworldj.socialsetting.server.service.verify.IVerifyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AuthService implements IAuthService {
    
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SubSettingRepository subSettingRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    private final IVerifyService verifyService;
    private final IMailService mailService;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        final UserEntity userEntity = UserEntity.builder()
                .email(signUpRequestDto.getEmail())
                .username(signUpRequestDto.getUsername())
                .profileName(signUpRequestDto.getProfileName())
                .encryptedPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .publicId(UUID.randomUUID().toString())
                .build();

        final UserEntity savedUserEntity = userRepository.save(userEntity);

        final SubSettingEntity subSettingEntity = SubSettingEntity.builder()
                .userEntity(savedUserEntity)
                .name(userEntity.getUsername())
                .description("Profile")
                .isProfile(true)
                .build();

        final SubSettingEntity savedSubSetting = subSettingRepository.save(subSettingEntity);

        final SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .subSettingId(savedSubSetting.getSubSettingId())
                .userId(savedUserEntity.getUserId())
                .subSettingEntity(savedSubSetting)
                .userEntity(savedUserEntity)
                .build();

        subscriptionRepository.save(subscriptionEntity);

        final VerificationTokenEntity verificationToken = verifyService.createVerificationToken(savedUserEntity);

        final String activationLink = "http://localhost:8080/api/auth/token/verify/" + verificationToken.getVerificationTokenId();

        final String body = "Thank you for signing up to Social Setting, " +
                "If you are using a mobile device please copy this code " + verificationToken.getCode() + " or\n" +
                "click on the below url to activate your account \n" + "<a href=\"" + activationLink + "\"> </a>";

        final NotificationEmail email = new NotificationEmail("Please activate your account ", userEntity.getEmail(), body);

        mailService.sendMail(email);
    }

    @Override
    public AuthenticationResponseDto verifyAccount(int code) {
        final VerificationTokenEntity verificationTokenEntity = verificationTokenRepository.findByCode(code)
                .orElseThrow(() -> new SocialSettingException("Verification Code not found", HttpStatus.NOT_FOUND));
        final UserEntity userEntity = verificationTokenEntity.getUserEntity();
        userEntity.setEnabled(true);
        verificationTokenRepository.delete(verificationTokenEntity);
        return AuthenticationResponseDto.builder()
                .username(userEntity.getUsername())
                .publicId(userEntity.getPublicId())
                .profileName(userEntity.getProfileName())
                .build();
    }

    @Override
    public UserEntity currentUser() {
        final String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new SocialSettingException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<String> usernames() {
        return userRepository.findAll().stream().map(UserEntity::getUsername)
                .collect(Collectors.toList());
    }
}
