package UniFest.service;

import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.booth.service.BoothService;
import UniFest.dto.request.booth.BoothCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class BoothServiceTest {

    @Autowired
    private BoothRepository boothRepository;
    @Autowired
    private BoothService boothService;


//    @BeforeEach
//    void setUp() {
//        boothRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("부스 생성")
//    void createBooth() {
//        BoothCreateRequest.builder().category("주점")
//                .name("부부스").isEnabled(true).latitude(10.0f).longitude(10.0f).festivalId(1L)
//                                                .
//    }
//    @Test
//    @DisplayName("특정 부스 조회")
//    void createBooth() {
//
//    }    @Test
//    @DisplayName("특정 축제 내 전체부스 조회")
//    void createBooth() {
//
//    }


}
