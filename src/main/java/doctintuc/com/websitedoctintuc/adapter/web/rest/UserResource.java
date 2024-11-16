package doctintuc.com.websitedoctintuc.adapter.web.rest;

import doctintuc.com.websitedoctintuc.application.request.LoginRequest;
import doctintuc.com.websitedoctintuc.domain.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Validated
@Api(tags = "Users Resource")
public interface UserResource {

    @ApiOperation(value = "Create new user")
    @PostMapping("/no-auth/create-user")
    ResponseEntity<?> create(@RequestBody UserDTO userDTO, HttpServletRequest request);

    @ApiOperation(value = "Create new user by admin")
    @PostMapping("/admin/create-user-by-admin")
    ResponseEntity<?> createAccountByAdmin(@RequestBody UserDTO userDTO, HttpServletRequest request);

    @ApiOperation(value = "Get user by id")
    @GetMapping("/both/get-user/{id}")
    ResponseEntity<?> get(@PathVariable Integer id);

    @ApiOperation(value = "Update user")
    @PostMapping("/both/update-user/{userId}")
    ResponseEntity<?> update(@PathVariable("userId") Integer userId,
                             @RequestBody UserDTO userDTO, HttpServletRequest request);

    @ApiOperation(value = "Delete user by id")
    @PostMapping("/admin/delete-user/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id);

    @ApiOperation(value = "Search all user")
    @GetMapping("/admin/search-all")
    ResponseEntity<?> searchAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name = "size", required = false, defaultValue = "10") Integer size);


    @ApiOperation(value = "Login")
    @PostMapping("/no-auth/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);


    @ApiOperation(value = "logout")
    @GetMapping("/both/logout")
    ResponseEntity<?> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response);
}
