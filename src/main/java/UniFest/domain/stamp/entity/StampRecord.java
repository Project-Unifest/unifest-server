package UniFest.domain.stamp.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.booth.entity.Booth;
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

    private String deviceId;

    public StampRecord(Booth booth, String deviceId) {
        this.booth = booth;
        this.deviceId = deviceId;
    }
}
