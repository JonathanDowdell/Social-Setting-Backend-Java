package com.mettaworldj.socialsetting.server.service.verify;

import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.model.VerificationTokenEntity;
import com.mettaworldj.socialsetting.server.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerifyService implements IVerifyService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationTokenEntity createVerificationToken(UserEntity userEntity) {
        final VerificationTokenEntity verificationTokenEntity = VerificationTokenEntity.builder()
                .verificationTokenId(UUID.randomUUID().toString())
                .userEntity(userEntity)
                .code(generateCode())
                .build();
        return verificationTokenRepository.save(verificationTokenEntity);
    }

    private int generateCode() {
        // Create Random Code with 7 digits
        Random random = new Random();
        return random.nextInt(9000000) + 1000000;
    }
}
