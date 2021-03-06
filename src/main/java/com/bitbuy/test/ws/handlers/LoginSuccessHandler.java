package com.bitbuy.test.ws.handlers;

import com.bitbuy.test.ws.persistence.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication.isAuthenticated()) {
            UserEntity principal = (UserEntity) authentication.getPrincipal();
            new DefaultRedirectStrategy().sendRedirect(request, response, "/users/" + principal.getId());
        }
    }

}
