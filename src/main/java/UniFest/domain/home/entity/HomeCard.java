package UniFest.domain.home.entity;

import UniFest.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "home_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeCard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="home_card_id")
    private Long id;

    private String thumbnailImgUrl;
    private String detailImgUrl;

    public HomeCard(String thumbnailImgUrl,String detailImgUrl) {
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.detailImgUrl = detailImgUrl;
    }
}
