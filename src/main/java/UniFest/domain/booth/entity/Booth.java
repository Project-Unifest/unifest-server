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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "booth_category", length = 50, nullable = false)
    private BoothCategory category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "detail", length = 100)
    private String detail;

    @Column(name = "thumbnail", length = 2500)
    private String thumbnail;

    @Column(name = "warning", length = 100)
    private String warning;

    @Column(name = "enabled")
    private boolean enabled = true;

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> lmgList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waiting> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoothSchedule> scheduleList = new ArrayList<>();

    private String location;

    private double latitude;

    private double longitude;

    @Builder
    public Booth(String name, BoothCategory category, String description, String detail, String thumbnail,
                 String warning, boolean enabled, String location, double latitude, double longitude, Festival festival) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.detail = detail;
        this.thumbnail = thumbnail;
        this.warning = warning;
        this.enabled = enabled;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.festival = festival;
    }
    public int getLikesCount(){
        return this.likesList.size();
    }

    public void updateEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCategory(BoothCategory category) {
        this.category = category;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateDetail(String detail) {
        this.detail = detail;
    }

    public void updateThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void updateWarning(String warning) {
        this.warning = warning;
    }

    public void updateLocation(String location) {
        this.location = location;
    }

    public void updateLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void updateLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMember(Member member){
        this.member = member;
        member.getBoothList().add(this);
    }

}
