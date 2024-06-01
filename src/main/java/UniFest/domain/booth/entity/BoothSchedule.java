package UniFest.domain.booth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "booth_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booth_schedule_id")
    private Long id;

    private LocalDate openDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;
    @Builder
    public BoothSchedule(LocalDate openDate){
        this.openDate = openDate;
    }

    public void setBooth(Booth booth){
        this.booth = booth;
        booth.getScheduleList().add(this);
    }

}
