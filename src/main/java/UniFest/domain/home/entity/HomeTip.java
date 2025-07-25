package UniFest.domain.home.entity;

import UniFest.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "home_tip")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeTip extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="home_tip_id")
    private Long id;

    private String tipContent;
    public HomeTip(String tipContent) {
        this.tipContent = tipContent;
    }
}
