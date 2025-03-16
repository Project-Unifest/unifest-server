package UniFest.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "booth_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothSchedule implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booth_schedule_id")
    private Long id;

    private LocalDate openDate;

    private LocalTime openTime;
    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;
    @Builder
    public BoothSchedule(LocalDate openDate, LocalTime openTime, LocalTime closeTime){
        this.openDate = openDate;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void setBooth(Booth booth){
        this.booth = booth;
        booth.getScheduleList().add(this);
    }

    @Override
    public int compareTo(Object o) {
        BoothSchedule compBooth = (BoothSchedule)o;
        if(compBooth.getOpenDate().isAfter(this.openDate))
            return -1;
        else{
            return 1;
        }
    }
}
