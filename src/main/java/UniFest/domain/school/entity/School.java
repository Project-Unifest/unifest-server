package UniFest.domain.school.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.festival.entity.Festival;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "school")
public class School extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="school_id")
    private Long id;

    private String name;

    private String region;

    private String address;

    private String thumbnail;

    private float latitude;

    private float longitude;

    public School(String name, String region, String address, String thumbnail, float latitude,
            float longitude) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.thumbnail = thumbnail;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Festival> festivalList = new ArrayList<>();

}
