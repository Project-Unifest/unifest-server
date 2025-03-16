package UniFest.domain.school.entity;

import UniFest.global.common.BaseEntity;
import UniFest.domain.festival.entity.Festival;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="school_id")
    private Long id;

    private String name;

    private String region;

    private String address;

    private String thumbnail;

    private double latitude;

    private double longitude;

    public School(String name, String region, String address, String thumbnail, double latitude,
                  double longitude) {
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
