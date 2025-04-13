package UniFest.domain.festival.entity;

import UniFest.domain.stamp.entity.StampRecord;
import UniFest.global.common.BaseEntity;
import UniFest.domain.school.entity.School;
import UniFest.domain.stamp.entity.StampInfo;
import UniFest.domain.star.entity.Enroll;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "festival")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="festival_id")
    private Long id;

    @Setter
    private String name;

    @Setter
    @Column(name = "description", length = 500)
    private String description;

    @Setter
    @Column(name = "thumbnail", length = 2500)
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "festival")
    private List<Enroll> enrollList = new ArrayList<>();

    @Setter
    private LocalDate beginDate;

    @Setter
    private LocalDate endDate;

    @OneToMany(mappedBy = "festival")
    private List<StampRecord> stampRecordList = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "festival", fetch = FetchType.LAZY)
    private StampInfo stampInfo;

    public void setSchool(School school){
        this.school = school;
        school.getFestivalList().add(this);
    }

    public Festival(String name, String description, String thumbnail, School school,
            LocalDate beginDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.school = school;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }
}
