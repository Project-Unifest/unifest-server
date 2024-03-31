package UniFest.security.userdetails;

import UniFest.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MemberDetails implements UserDetails {
    private Long memberId;
    private String email;
    private String password;
    private String role;

    public MemberDetails(Long memberId,String email, String password, String role){
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    private final List<GrantedAuthority> NORMAL_ROLES = AuthorityUtils.createAuthorityList("ROLE_NORMAL");
    private final List<GrantedAuthority> VERIFIED_ROLES = AuthorityUtils.createAuthorityList("ROLE_VERIFIED");
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return createAuthorities(this.role);
    }

    private Collection<? extends GrantedAuthority> createAuthorities(String role) {
        if (role.equals("NORMAL") ) return NORMAL_ROLES;
        else if (role.equals("ADMIN")) return ADMIN_ROLES;
        else return VERIFIED_ROLES;
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
