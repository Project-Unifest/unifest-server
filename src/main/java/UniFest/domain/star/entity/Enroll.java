package UniFest.domain.star.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.festival.entity.Festival;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;

import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "enroll")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enroll extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="enroll_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private Star star;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    public Enroll(Festival festival, Star star, LocalDate visitDate) {
        this.festival = festival;
        this.star = star;
        this.visitDate = visitDate;
    }
}
