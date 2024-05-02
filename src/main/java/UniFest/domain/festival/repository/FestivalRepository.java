package UniFest.domain.festival.repository;

import UniFest.domain.festival.entity.Festival;
import UniFest.dto.response.festival.FestivalSearchResponse;
import UniFest.dto.response.festival.TodayFestivalInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

    //학교
    @Query("select new UniFest.dto.response.festival.FestivalSearchResponse(f.id, s.id, s.thumbnail, s.name, s.region, f.name, f.beginDate, f.endDate, s.latitude, s.longitude)"
            + " from School s join Festival f on f.school.id=s.id"
            + " where s.name like :schoolName"
            + " order by s.name")
    List<FestivalSearchResponse> findFestivalBySchool(@Param("schoolName") String schoolName);

    //전체
    @Query("select new UniFest.dto.response.festival.FestivalSearchResponse(f.id, s.id, s.thumbnail, s.name, s.region, f.name, f.beginDate, f.endDate, s.latitude, s.longitude)"
            + " from School s join Festival f on f.school.id=s.id"
            + " order by s.name")
    List<FestivalSearchResponse> findAllFestival();

    //지역
    @Query("select new UniFest.dto.response.festival.FestivalSearchResponse(f.id, s.id, s.thumbnail, s.name, s.region, f.name, f.beginDate, f.endDate, s.latitude, s.longitude)"
            + " from School s join Festival f on f.school.id=s.id"
            + " where s.region = :region"
            + " order by s.name")
    List<FestivalSearchResponse> findFestivalByRegion(@Param("region") String region);

    //다가오는
    @Query("select new UniFest.dto.response.festival.FestivalSearchResponse(f.id, s.id, s.thumbnail, s.name, s.region, f.name, f.beginDate, f.endDate, s.latitude, s.longitude)"
            + " from School s join Festival f on f.school.id=s.id"
            + " where f.beginDate > CURRENT_DATE"
            + " order by f.endDate")
    List<FestivalSearchResponse> findAfterFestival(Pageable pageable);


    //오늘
    @Query("select new UniFest.dto.response.festival.TodayFestivalInfo(s.id, s.name, s.thumbnail, f.id, f.name, f.beginDate, f.endDate)"
            + " from School s join Festival f on f.school.id=s.id"
            + " where :date between f.beginDate and f.endDate "
            + " order by s.name")
    List<TodayFestivalInfo> findFestivalByDate(@Param("date") LocalDate date);
}
