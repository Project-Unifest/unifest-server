package UniFest.domain.school.entity;

import UniFest.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

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

    private float longtitude;

}
