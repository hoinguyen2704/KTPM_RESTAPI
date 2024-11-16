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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class) // Sử dụng MockitoExtension để tích hợp Mockito với JUnit 5 (Jupiter).
public class AnTest { // Khai báo lớp test cho lớp `UserServiceImpl`.

    @Mock
    private UserRepository userRepository; // Khai báo mock cho UserRepository để giả lập hành vi của repository.

    @Mock
    private RoleRepository roleRepository; // Khai báo mock cho RoleRepository.

    @Mock
    private JwtUtils jwtUtils; // Khai báo mock cho JwtUtils (chức năng xử lý token JWT).

    @InjectMocks
    private UserServiceImpl userServiceImpl; // Khai báo đối tượng `userServiceImpl` sẽ được inject các mock vào.

    private UserDTO userDTO; // Đối tượng DTO chứa thông tin người dùng cần cập nhật.
    private User mockUser; // Đối tượng mockUser giả lập người dùng admin.
    private User existingUser; // Đối tượng existingUser giả lập người dùng hiện tại trong cơ sở dữ liệu.
    private Role adminRole; // Đối tượng role giả lập vai trò admin.
    private Role userRole; // Đối tượng role giả lập vai trò người dùng.

    @BeforeEach // Phương thức này sẽ được gọi trước mỗi bài test.
    public void setUp() {
        // Khởi tạo các đối tượng giả lập với dữ liệu mẫu.
        userDTO = new UserDTO();
        userDTO.setFullName("Updated Name");
        userDTO.setEmail("updated@example.com");
        userDTO.setPhone("987654321");
        userDTO.setAddress("Updated Address");
        userDTO.setGender("Female");
        userDTO.setAvatar("updatedAvatar.png");
        userDTO.setBirthday("01/01/1990");

        // Khởi tạo các vai trò.
        adminRole = new Role();
        adminRole.setRoleName(EnumRole.ROLE_ADMIN);

        userRole = new Role();
        userRole.setRoleName(EnumRole.ROLE_USER);

        // Khởi tạo người dùng mock và người dùng thực tế.
        mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setFullName("Admin User");
        mockUser.setRole(adminRole);
        mockUser.setId(1);

        existingUser = new User();
        existingUser.setUsername("user1");
        existingUser.setFullName("User One");
        existingUser.setRole(userRole);
        existingUser.setId(2);
    }

    @Test
    public void testUpdate_Fail_User() {
        // Mô phỏng hành vi khi người dùng cố gắng cập nhật thông tin không thành công (do không có quyền).
        when(userRepository.findById(2)).thenReturn(Optional.of(existingUser)); // Giả lập tìm kiếm người dùng theo ID.
        when(jwtUtils.getUserByToken(anyString())).thenReturn(mockUser.getUsername()); // Giả lập lấy tên người dùng từ token.
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser); // Giả lập tìm người dùng theo tên.
        when(jwtUtils.validationToken(anyString())).thenReturn(true); // Giả lập việc xác thực token hợp lệ.

        // Mô phỏng yêu cầu HTTP với header Authorization.
        HttpServletRequest request = mock(HttpServletRequest.class); // Tạo mock HttpServletRequest.
        when(request.getHeader("Authorization")).thenReturn("Bearer token123"); // Giả lập header Authorization.

        // Thực thi phương thức update và kiểm tra xem có ném exception không.
        Exception exception = assertThrows(VsException.class, () -> {
            userServiceImpl.update(2, userDTO, request); // Gọi phương thức update và kỳ vọng có lỗi.
        });

        // Kiểm tra thông điệp exception có đúng không.
        assertEquals(DevMessageConstant.Common.AUTHORIZED, exception.getMessage());
    }

    @Test
    public void testUpdate_UnauthorizedRole() {
        // Kiểm tra trường hợp người dùng không có quyền thực hiện update.
        when(userRepository.findById(2)).thenReturn(Optional.of(existingUser)); // Giả lập tìm kiếm người dùng.
        when(jwtUtils.getUserByToken(anyString())).thenReturn(mockUser.getUsername()); // Giả lập lấy tên người dùng từ token.
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser); // Giả lập tìm người dùng theo tên.
        when(jwtUtils.validationToken(anyString())).thenReturn(true); // Giả lập xác thực token.

        // Mô phỏng yêu cầu HTTP với header Authorization.
        HttpServletRequest request = mock(HttpServletRequest.class); // Tạo mock HttpServletRequest.
        when(request.getHeader("Authorization")).thenReturn("Bearer token123"); // Giả lập header Authorization.

        // Thực thi phương thức update và kiểm tra có exception không (do role không khớp).
        Exception exception = assertThrows(VsException.class, () -> {
            userServiceImpl.update(2, userDTO, request); // Gọi phương thức update và kỳ vọng có lỗi.
        });

        // Kiểm tra thông điệp exception.
        assertEquals(DevMessageConstant.Common.AUTHORIZED, exception.getMessage());
    }

    @Test
    public void testUpdate_Success_User() {
        // Kiểm tra trường hợp người dùng bình thường cập nhật thông tin của chính mình thành công.
        existingUser.setRole(userRole); // Đặt role cho người dùng là user.
        when(userRepository.findById(2)).thenReturn(Optional.of(existingUser)); // Giả lập tìm kiếm người dùng.
        when(jwtUtils.getUserByToken(anyString())).thenReturn(existingUser.getUsername()); // Lấy tên người dùng từ token.
        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(existingUser); // Giả lập tìm người dùng theo tên.
        when(jwtUtils.validationToken(anyString())).thenReturn(true); // Giả lập xác thực token hợp lệ.

        // Mô phỏng yêu cầu HTTP với header Authorization.
        HttpServletRequest request = mock(HttpServletRequest.class); // Tạo mock HttpServletRequest.
        when(request.getHeader("Authorization")).thenReturn("Bearer token123"); // Giả lập header Authorization.

        // Thực thi phương thức update và kiểm tra kết quả.
        User updatedUser = userServiceImpl.update(2, userDTO, request); // Gọi phương thức update.

        // Kiểm tra kết quả cập nhật có đúng không.
        assertNotNull(updatedUser); // Kiểm tra đối tượng đã được cập nhật.
        assertEquals("Updated Name", updatedUser.getFullName()); // Kiểm tra thông tin fullName.
        assertEquals("updated@example.com", updatedUser.getEmail()); // Kiểm tra email.
        assertEquals("987654321", updatedUser.getPhone()); // Kiểm tra phone.
        assertEquals("Updated Address", updatedUser.getAddress()); // Kiểm tra address.
        assertEquals("Female", updatedUser.getGender()); // Kiểm tra gender.
        assertEquals("updatedAvatar.png", updatedUser.getAvatar()); // Kiểm tra avatar.
    }

}

