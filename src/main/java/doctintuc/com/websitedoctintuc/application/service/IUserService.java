package doctintuc.com.websitedoctintuc.application.service;

import doctintuc.com.websitedoctintuc.application.request.LoginRequest;
import doctintuc.com.websitedoctintuc.application.response.UserResponse;
import doctintuc.com.websitedoctintuc.domain.dto.UserDTO;
import doctintuc.com.websitedoctintuc.domain.entity.User;
import doctintuc.com.websitedoctintuc.domain.pagine.PaginateDTO;
import io.swagger.models.auth.In;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    User create(UserDTO accountDTO, HttpServletRequest request);

    User get(int id);

    User update(Integer userId , UserDTO accountDTO, HttpServletRequest request);

    String delete(int id);

    PaginateDTO<User> searchAll(Integer page, Integer size);

    UserResponse login(LoginRequest loginRequest);

    String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response);

}
