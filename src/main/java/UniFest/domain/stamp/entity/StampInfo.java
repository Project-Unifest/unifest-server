package UniFest.domain.stamp.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.festival.entity.Festival;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "stamp_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StampInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stamp_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @OneToMany(mappedBy = "stampInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StampRecord> stampRecordList = new ArrayList<>();

    private String defaultImgUrl;

    private String usedImgUrl;

    public StampInfo(Festival festival, Booth booth, String defaultImgUrl, String usedImgUrl) {
        this.festival = festival;
        this.booth = booth;
        this.defaultImgUrl = defaultImgUrl;
        this.usedImgUrl = usedImgUrl;
    }
}
