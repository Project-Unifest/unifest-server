package UniFest.domain.festival.entity;

import UniFest.domain.Device;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "interest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interest_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @Column(nullable = false)
    private String deviceId;

    @Builder
    public Interest(Festival festival, Device device) {
        this.festival = festival;
        this.deviceId = device.getDeviceId();
    }
}
