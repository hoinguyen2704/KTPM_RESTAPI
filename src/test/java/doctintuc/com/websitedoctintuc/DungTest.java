package doctintuc.com.websitedoctintuc;

import doctintuc.com.websitedoctintuc.application.constants.DevMessageConstant;
import doctintuc.com.websitedoctintuc.application.constants.EnumRole;
import doctintuc.com.websitedoctintuc.application.jwt.JwtUtils;
import doctintuc.com.websitedoctintuc.application.repository.RoleRepository;
import doctintuc.com.websitedoctintuc.application.repository.UserRepository;
import doctintuc.com.websitedoctintuc.application.service.impl.UserServiceImpl;
import doctintuc.com.websitedoctintuc.config.exception.VsException;
import doctintuc.com.websitedoctintuc.domain.dto.UserDTO;
import doctintuc.com.websitedoctintuc.domain.entity.Role;
import doctintuc.com.websitedoctintuc.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DungTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserDTO accountDTO;
    private User mockUser;
    private Role adminRole;
    private Role userRole;

    @BeforeEach
    public void setUp() {
        // Mock dữ liệu đầu vào
        accountDTO = new UserDTO();
        accountDTO.setUsername("user1");
        accountDTO.setFullName("John Doe");
        accountDTO.setEmail("johndoe@example.com");
        accountDTO.setPassword("password123");
        accountDTO.setGender("Male");
        accountDTO.setPhone("123456789");
        accountDTO.setAddress("123 Street");
        accountDTO.setAvatar("avatar.png");
        accountDTO.setBirthday("01/01/2002");

        mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setFullName("Admin User");
        adminRole = new Role();
        adminRole.setRoleName(EnumRole.ROLE_SUPER_ADMIN);
        mockUser.setRole(adminRole);

        userRole = new Role();
        userRole.setRoleName(EnumRole.ROLE_USER);
    }

    @Test
    public void testCreate_Success_SuperAdmin() {
        // Mô phỏng dữ liệu
        when(userRepository.existsByUsername(accountDTO.getUsername())).thenReturn(false);
        when(jwtUtils.getUserByToken(anyString())).thenReturn(mockUser.getUsername());
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser);
        when(roleRepository.findRoleByRoleName(EnumRole.ROLE_ADMIN)).thenReturn(adminRole);

        // Mock Authorization header
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");

        // Thực thi phương thức
        User createdUser = userServiceImpl.create(accountDTO, request);

        // Kiểm tra kết quả
        assertNotNull(createdUser);
        assertEquals("Admin User", createdUser.getCreateBy());
        assertEquals(EnumRole.ROLE_SUPER_ADMIN, createdUser.getRole().getRoleName());
    }

    @Test
    public void testCreate_Success_User() {
        // Mô phỏng dữ liệu
        when(userRepository.existsByUsername(accountDTO.getUsername())).thenReturn(false);
        when(roleRepository.findRoleByRoleName(EnumRole.ROLE_USER)).thenReturn(userRole);

        // Mock Authorization header không hợp lệ
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        // Thực thi phương thức
        User createdUser = userServiceImpl.create(accountDTO, request);

        // Kiểm tra kết quả
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getCreateBy());
        assertEquals(EnumRole.ROLE_USER, createdUser.getRole().getRoleName());
    }

    @Test
    public void testCreate_UsernameAlreadyExists() {
        // Mô phỏng dữ liệu: username đã tồn tại
        when(userRepository.existsByUsername(accountDTO.getUsername())).thenReturn(true);

        // Mock request
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Thực thi và kiểm tra ngoại lệ
        Exception exception = assertThrows(VsException.class, () -> {
            userServiceImpl.create(accountDTO, request);
        });

        // Kiểm tra thông báo lỗi
        assertEquals(
                String.format(DevMessageConstant.Common.EXITS_USERNAME, accountDTO.getUsername()),
                exception.getMessage()
        );
    }

    @Test
    public void testCreate_InvalidBirthdayFormat() {
        // Mô phỏng dữ liệu với định dạng ngày sinh không hợp lệ
        accountDTO.setBirthday("invalid-date");

        // Mock dữ liệu
        when(userRepository.existsByUsername(accountDTO.getUsername())).thenReturn(false);
        when(roleRepository.findRoleByRoleName(EnumRole.ROLE_USER)).thenReturn(userRole);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        // Thực thi phương thức
        User createdUser = userServiceImpl.create(accountDTO, request);

        // Kiểm tra kết quả
        assertNull(createdUser);
    }

    @Test
    public void testCreate_ExceptionWhileSaving() {
        // Mô phỏng dữ liệu: phát sinh ngoại lệ khi lưu User
        when(userRepository.existsByUsername(accountDTO.getUsername())).thenReturn(false);
        when(roleRepository.findRoleByRoleName(EnumRole.ROLE_USER)).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        // Thực thi và kiểm tra ngoại lệ
        Exception exception = assertThrows(VsException.class, () -> {
            userServiceImpl.create(accountDTO, request);
        });

        String errorMessage = exception.getMessage();

        // Kiểm tra thông báo lỗi
        assertTrue(errorMessage.equals("Register failed : The error is : java.lang.RuntimeException: Database error"));
    }

}
