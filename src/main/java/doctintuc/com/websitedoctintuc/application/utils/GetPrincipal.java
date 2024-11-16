package doctintuc.com.websitedoctintuc.application.utils;

import doctintuc.com.websitedoctintuc.application.constants.EnumRole;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class GetPrincipal {

    public String getCurrentPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String authority = ((UserDetails) principal).getAuthorities().toString();
            if (authority.equals(EnumRole.ROLE_ADMIN.toString()) ||
                    authority.equals(EnumRole.ROLE_SUPER_ADMIN
                            .toString())) {
                return authority;
            }
        }
        return null;
    }
}
