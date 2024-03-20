package UniFest.domain.booth.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.likes.entity.Likes;
import UniFest.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "booth")
public class Booth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booth_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "detail", length = 100)
    private String detail;

    @Column(name = "thumbnail", length = 2500)
    private String thumbnail;

    @Column(name = "warning", length = 100)
    private String warning;

    @Column(name = "is_enabled", length = 100)
    private boolean isEnabled;

    @Column(name = "img_list")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> lmgList = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Menu> menuList = new ArrayList<>();

    private float latitude;

    private float longtitude;

}
