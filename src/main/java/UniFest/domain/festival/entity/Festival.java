package UniFest.domain.festival.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.school.entity.School;
import UniFest.domain.star.entity.Enroll;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "festival")
public class Festival extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="festival_id")
    private Long id;

    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "thumbnail", length = 2500)
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "festival")
    private List<Enroll> enrollList = new ArrayList<>();

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    public void setSchool(School school){
        this.school = school;
        school.getFestivalList().add(this);
    }



}
