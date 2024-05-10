package UniFest.domain.member.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.school.entity.School;
import UniFest.domain.waiting.entity.Waiting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booth> boothList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    private String phoneNum;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_role",nullable = false)
    private MemberRole memberRole;

    @Builder
    public Member(String email, String password , School school, String phoneNum, MemberRole memberRole){
        this.email = email;
        this.password = password;
        this.school = school;
        this.phoneNum = phoneNum;
        this.memberRole = memberRole;
    }

    public void updateRole(MemberRole role){
        this.memberRole = role;
    }


}
