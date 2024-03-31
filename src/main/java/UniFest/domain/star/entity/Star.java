package UniFest.domain.star.entity;

import UniFest.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "star")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Star extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="star_id")
    private Long id;

    private String name;

    private String img;

    public Star(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
