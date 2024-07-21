package UniFest.domain.waiting.entity;

public enum ReservationStatus {
    RESERVED("예약됨"),
    COMPLETED("예약 완료됨"),
    CANCELED("예약 취소됨");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
