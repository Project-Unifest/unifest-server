package UniFest.domain.waiting.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.booth.entity.Booth;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
@Table(
        name = "waiting",
        indexes = {
                @Index(name = "idx_device_id", columnList = "deviceId"),
                @Index(name = "idx_booth_id", columnList = "booth_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Waiting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER 필요한 경우는 따로 명시
    @JoinColumn(name = "booth_id")
    private Booth booth;

    private String tel;

    private String deviceId;

    private int partySize;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String waitingStatus = "RESERVED";


    private String fcmToken = null;

    @Builder
    public Waiting(Booth booth, String deviceId, String tel, int partySize) {
        this.booth = booth;
        this.deviceId = deviceId;
        this.tel = tel;
        this.partySize = partySize;
        this.waitingStatus = "RESERVED";
    }

    @Builder
    public Waiting(Booth booth, String deviceId, String tel, int partySize, String fcmToken) {
        this.booth = booth;
        this.deviceId = deviceId;
        this.tel = tel;
        this.partySize = partySize;
        this.waitingStatus = "RESERVED";
        this.fcmToken = fcmToken;
    }

    public Waiting() {

    }
}
