package com.bitbuy.test.service;

import com.bitbuy.test.ws.persistence.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userCheck")
public class UserSecurityService {
    public boolean youAreWhoYouSay(Authentication authentication, UUID uuid) {
        UserEntity principal = (UserEntity) authentication.getPrincipal();
        return authentication.isAuthenticated() && principal.getId().equals(uuid);
    }
}
