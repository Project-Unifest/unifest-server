package UniFest.domain.star.entity;

import UniFest.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "star")
public class Star extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="star_id")
    private Long id;

    private String name;

    private String img;

    @OneToMany(mappedBy = "star")
    private List<Enroll> enrollList = new ArrayList<>();
}
