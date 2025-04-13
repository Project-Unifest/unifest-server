package UniFest.domain.stamp.entity;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.festival.entity.Festival;
import UniFest.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stamp_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StampRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stamp_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    private String deviceId;

    public StampRecord(Booth booth, String deviceId, Festival festival) {
        this.booth = booth;
        this.deviceId = deviceId;
        this.festival = festival;
    }
}
