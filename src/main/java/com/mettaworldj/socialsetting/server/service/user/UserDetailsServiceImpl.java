package com.mettaworldj.socialsetting.server.service.user;

import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new SocialSettingException("User does not exist", HttpStatus.NOT_FOUND));
        return new User(userEntity.getUsername(), userEntity.getEncryptedPassword(), userEntity.getEnabled(), true, true, true, grantedAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

}
