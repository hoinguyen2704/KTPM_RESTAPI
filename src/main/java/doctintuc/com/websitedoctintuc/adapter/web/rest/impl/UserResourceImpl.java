package doctintuc.com.websitedoctintuc.adapter.web.rest.impl;

import doctintuc.com.websitedoctintuc.adapter.web.base.RestApiV1;
import doctintuc.com.websitedoctintuc.adapter.web.base.VsResponseUtil;
import doctintuc.com.websitedoctintuc.adapter.web.rest.UserResource;
import doctintuc.com.websitedoctintuc.application.jwt.JwtUtils;
import doctintuc.com.websitedoctintuc.application.request.LoginRequest;
import doctintuc.com.websitedoctintuc.application.service.IUserService;
import doctintuc.com.websitedoctintuc.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestApiV1
@RequiredArgsConstructor
public class UserResourceImpl implements UserResource {
    private final IUserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> create(UserDTO userDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(userService.create(userDTO, request));
    }

    @Override
    public ResponseEntity<?> createAccountByAdmin(UserDTO userDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(userService.create(userDTO, request));
    }

    @Override
    public ResponseEntity<?> get(Integer id) {
        return VsResponseUtil.ok(userService.get(id));
    }


    @Override
    public ResponseEntity<?> update(Integer userId , UserDTO userDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(userService.update(userId , userDTO, request));
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        return VsResponseUtil.ok(userService.delete(id));
    }

    @Override
    public ResponseEntity<?> searchAll(Integer page, Integer size) {
        return VsResponseUtil.ok(userService.searchAll(page, size));
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        return VsResponseUtil.ok(userService.login(loginRequest));
    }

    @Override
    public ResponseEntity<?> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        return VsResponseUtil.ok(userService.logout(authentication, request, response));
    }
}
