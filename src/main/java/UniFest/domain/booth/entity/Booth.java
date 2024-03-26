package UniFest.domain.booth.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.likes.entity.Likes;
import UniFest.domain.member.entity.Member;
import UniFest.domain.menu.entity.Menu;
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
@Table(name = "booth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booth_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "detail", length = 100)
    private String detail;

    @Column(name = "thumbnail", length = 2500)
    private String thumbnail;

    @Column(name = "warning", length = 100)
    private String warning;

    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> lmgList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waiting> waitingList = new ArrayList<>();

    private float latitude;

    private float longtitude;

    @Builder
    public Booth(String name, String category, String description, String detail, String thumbnail,
                 String warning, boolean isEnabled, float latitude, float longtitude, Festival festival) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.detail = detail;
        this.thumbnail = thumbnail;
        this.warning = warning;
        this.isEnabled = isEnabled;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.festival = festival;
    }

    public void setIsEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }

    public void setMember(Member member){
        this.member = member;
        member.getBoothList().add(this);
    }

}
