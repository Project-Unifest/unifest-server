package UniFest.security.userdetails;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MemberDetails implements UserDetails {
    private final Long memberId;
    private final String email;
    private final String password;
    private final String role;

    public MemberDetails(Long memberId, String email, String password, String role){
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.role = role; //TODO role enum으로 관리
    }

    private final List<GrantedAuthority> PENDING_ROLES = AuthorityUtils.createAuthorityList("ROLE_PENDING");
    private final List<GrantedAuthority> DENIED_ROLES = AuthorityUtils.createAuthorityList("ROLE_DENIED");
    private final List<GrantedAuthority> VERIFIED_ROLES = AuthorityUtils.createAuthorityList("ROLE_VERIFIED");
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
    private final List<GrantedAuthority> DEV_ROLES = AuthorityUtils.createAuthorityList("ROLE_DEV");
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return createAuthorities(this.role);
    }

    private Collection<? extends GrantedAuthority> createAuthorities(String role) {
        return switch (role) {
            case "DEV" -> DEV_ROLES;
            case "PENDING" -> PENDING_ROLES;
            case "DENIED" -> DENIED_ROLES;
            case "ADMIN" -> ADMIN_ROLES;
            default -> VERIFIED_ROLES;
        };
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
