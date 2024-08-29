package UniFest.domain.announcement.entity;

import UniFest.domain.audit.BaseEntity;
import UniFest.domain.booth.entity.Booth;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "megaphone")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Megaphone extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="megaphone_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @Column(name="msg_body", length = 4096, nullable = false)
    private String msgBody;

    @Column(name = "is_sent")
    private boolean isSent;

    @Column(name = "errorMessage", length = 255)
    private String errorMessage;

    @Builder
    public Megaphone(Booth booth, String msgBody) {
        this.booth = booth;
        this.msgBody = msgBody;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }
}
