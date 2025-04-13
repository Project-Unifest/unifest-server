package UniFest.service;

import UniFest.domain.festival.service.FestivalService;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.star.repository.EnrollRepository;
import UniFest.domain.festival.dto.response.TodayFestivalInfo;
import UniFest.domain.star.dto.response.EnrollInfo;
import UniFest.domain.star.dto.response.StarInfo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class) // MockitoExtension으로 테스트 확장 -> 이거 안하면 Config 땡겨오다 터짐
class FestivalServiceTest {

    @Mock
    private FestivalRepository festivalRepository;

    @Mock
    private EnrollRepository enrollRepository;

    // @InjectMocks: festivalService를 생성하면서, @Mock으로 선언된 객체를 의존성으로 주입
    @InjectMocks
    private FestivalService festivalService;

    @Test
    void testGetFesitvalByDateRevision() {
        // given
        LocalDate testDate = LocalDate.of(2024, 3, 15);

        // 1) mockFestivalInfo: 특정 날짜에 해당하는 축제 정보 (2개)
        List<TodayFestivalInfo> mockFestivalInfo = List.of(
                new TodayFestivalInfo(
                        1L, "MockSchool1", "mockThumb1", 100L,
                        "MockFestival1", testDate, testDate
                ),
                new TodayFestivalInfo(
                        2L, "MockSchool2", "mockThumb2", 200L,
                        "MockFestival2", testDate, testDate
                )
        );

        // 2) mockStarList: 축제 100번에는 스타 2명, 축제 200번에는 스타 1명
        List<EnrollInfo> mockStarList = List.of(
                new EnrollInfo(100L, 10L, "StarA", "imgA"),
                new EnrollInfo(100L, 11L, "StarB", "imgB"),
                new EnrollInfo(200L, 99L, "StarX", "imgX")
        );

        // Mockito stubbing: Repository가 특정 파라미터(date)를 받으면 우리가 지정한 mock 결과를 반환
        when(festivalRepository.findFestivalByDate(testDate)).thenReturn(mockFestivalInfo);
        when(enrollRepository.findByDate(testDate)).thenReturn(mockStarList);

        // when
        List<TodayFestivalInfo> result = festivalService.getFestivalByDateRevision(testDate);


        // then - 검증
        assertNotNull(result, "결과가 null 이면 안 됨");
        assertEquals(2, result.size(), "축제 리스트는 2건이어야 한다.");

        // 첫 번째 축제( festivalId=100 )에 스타 2명 연결되었는지
        TodayFestivalInfo fes1 = result.get(0);
        assertEquals(100L, fes1.getFestivalId());
        assertEquals(2, fes1.getStarList().size(), "festivalId=100에는 스타 2명이 매핑되어야 한다.");

        StarInfo starA = fes1.getStarList().get(0);
        assertEquals(10L, starA.getStarId());
        assertEquals("StarA", starA.getName());

        StarInfo starB = fes1.getStarList().get(1);
        assertEquals(11L, starB.getStarId());
        assertEquals("StarB", starB.getName());

        // 두 번째 축제( festivalId=200 )에 스타 1명 연결되었는지
        TodayFestivalInfo fes2 = result.get(1);
        assertEquals(200L, fes2.getFestivalId());
        assertEquals(1, fes2.getStarList().size(), "festivalId=200에는 스타 1명이 매핑되어야 한다.");

        StarInfo starX = fes2.getStarList().get(0);
        assertEquals(99L, starX.getStarId());
        assertEquals("StarX", starX.getName());

        // verify (Repository가 실제로 몇 번 호출되었는지 등 추가 확인)
        verify(festivalRepository, times(1)).findFestivalByDate(testDate);
        verify(enrollRepository, times(1)).findByDate(testDate);
    }
}

