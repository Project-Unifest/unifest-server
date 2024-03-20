package UniFest.domain.member.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.booth.entity.Booth;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 400)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    private String assign;

    private String phoneNum;

    private boolean isChecked;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_role",nullable = false)
    private MemberRole memberRole = MemberRole.NORMAL;

    @Builder
    public Member(String email, String password , String assign, String phoneNum, MemberRole memberRole){
        this.email = email;
        this.password = password;
        this.assign = assign;
        this.phoneNum = phoneNum;
        this.memberRole = memberRole;

    }

    public void updateRole(MemberRole role){
        this.memberRole = role;
    }


}
