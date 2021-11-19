package com.bitbuy.test.service;

import com.bitbuy.test.domain.exceptions.UserAlreadyExistsException;
import com.bitbuy.test.domain.UserData;
import com.bitbuy.test.ws.persistence.entities.UserEntity;
import com.bitbuy.test.ws.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
    }

    public void register(UserData user) throws UserAlreadyExistsException {
        String userName = user.getUserName();
        if(userRepository.findByUserName(userName).isPresent()){
            throw new UserAlreadyExistsException(userName);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findUserById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

}
