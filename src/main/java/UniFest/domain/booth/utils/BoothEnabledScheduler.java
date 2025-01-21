package UniFest.domain.booth.utils;

import UniFest.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BoothEnabledScheduler {

    private final BoothService boothService;

    // 매일 새벽 3시 30분 : 금일 운영하지 않는 부스 disabled 처리, 운영하는 부스 enabled 처리
    @Scheduled(cron = "0 30 3 * * ?")
    public void updateMemberStatus() {
        boothService.updateBoothEnabled();
        boothService.updateBoothDisabled();
        boothService.deleteBoothSchedule();
        log.info("booth-enabled updated successfully!");
    }
}
