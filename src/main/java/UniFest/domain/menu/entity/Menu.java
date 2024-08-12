package UniFest.domain.menu.entity;

import UniFest.domain.booth.entity.Booth;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long id;

    private String name;

    private int price;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ENOUGH'")
    private MenuStatus menuStatus = MenuStatus.ENOUGH;

    @Builder
    public Menu(String name, int price, String imgUrl, MenuStatus menuStatus){
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.menuStatus = menuStatus;
    }

    public void setBooth(Booth booth) {
        this.booth = booth;
        booth.getMenuList().add(this);
    }

    public void updateMenuStatus(MenuStatus menuStatus){
        this.menuStatus = menuStatus;
    }
}
