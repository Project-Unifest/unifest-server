package UniFest.domain.booth.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.likes.entity.Likes;
import UniFest.domain.megaphone.entity.Megaphone;
import UniFest.domain.member.entity.Member;
import UniFest.domain.menu.entity.Menu;
import UniFest.domain.stamp.entity.Stamp;
import UniFest.domain.waiting.entity.Waiting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Column(name = "name", length = 30)
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

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Waiting> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoothSchedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Megaphone> megaphoneList = new ArrayList<>();

    @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stamp> stampList = new ArrayList<>();

    private String location;

    private double latitude;

    private double longitude;

    //Waiting 가능 여부
    @ColumnDefault("0")
    private boolean waitingEnabled;

    //Waiting을 위한 Booth별 pin
    private String pin;

    private LocalTime openTime;
    private LocalTime closeTime;

    @ColumnDefault("0")
    private boolean stampEnabled;

    @Builder
    public Booth(String name, BoothCategory category, String description, String detail, String thumbnail,
                 String warning, boolean enabled, String location, double latitude, double longitude, Festival festival, boolean waitingEnabled,
                 LocalTime openTime, LocalTime closeTime) {
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
        this.waitingEnabled = waitingEnabled;
        this.openTime = openTime;
        this.closeTime = closeTime;
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

    public void updateWaitingEnabled(boolean enabled){
        this.waitingEnabled = enabled;
    }

    public void setMember(Member member){
        this.member = member;
        member.getBoothList().add(this);
    }

    //핀 번호 발급/재발급시 사용
    public String createPin(){
        Random random = new Random(System.currentTimeMillis());
        int tempIntPin = random.nextInt(10000);
        this.pin = String.format("%04d", tempIntPin);    //4자리 숫자 문자열

        return this.pin;
    }

    public void setOpeningHour(LocalTime openTime, LocalTime closeTime){
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void stampEnabled(boolean enabled){
        this.stampEnabled = enabled;
    }

    public void addStampList(Stamp stamp){
        this.stampList.add(stamp);
    }

    public void updateStampEnabled(Boolean stampEnabled) {
        this.stampEnabled = stampEnabled;
    }
}
