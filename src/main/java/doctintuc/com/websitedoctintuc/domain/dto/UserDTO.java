package doctintuc.com.websitedoctintuc.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "FullName is not blank")
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String birthday;
    private String phone;
    private String gender;
    private String address;
    private String avatar;
}
