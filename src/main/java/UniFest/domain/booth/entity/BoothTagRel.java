package UniFest.domain.booth.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "booth_tag_rel")
public class BoothTagRel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
