package UniFest.domain.stamp.entity;

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
    @JoinColumn(name = "stamp_info_id")
    private StampInfo stampInfo;

    private String deviceId;

    public StampRecord(StampInfo stampInfo, String deviceId) {
        this.stampInfo = stampInfo;
        this.deviceId = deviceId;
    }
}
