package cfg;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    USER,
    MODERATOR,
    MANAGER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}