package UniFest.service;

import UniFest.domain.admin.AdminService;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.BoothCategory;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.festival.service.FestivalService;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.domain.member.service.MemberService;
import UniFest.domain.school.entity.School;
import UniFest.domain.school.repository.SchoolRepository;
import UniFest.dto.request.festival.PostFestivalRequest;
import UniFest.dto.request.member.MemberSignUpRequest;
import UniFest.exception.FestivalNotFoundException;
import UniFest.exception.member.MemberNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private BoothRepository boothRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FestivalService festivalService;
    @Autowired
    private FestivalRepository festivalRepository;

    @Test
    @DisplayName("부스 소유자 변경")
    void changeBoothOwner() {
        long schoolId = schoolRepository.save(new School(
                "school", "0", "address",
                "thumbnail", 0, 0
        )).getId();

        long festivalId = festivalService.createFestival(
                new PostFestivalRequest(
                        schoolId, LocalDate.of(2024,9,28),
                        LocalDate.of(2024, 10, 28),
                        "festival", "description", "thumbnail"
                )
        );

        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest();
        memberSignUpRequest.setEmail("test@email.com");
        memberSignUpRequest.setPassword("password");
        memberSignUpRequest.setSchoolId(schoolId);
        memberSignUpRequest.setPhoneNum("010-0000-0000");
        long memberId = memberService.createMember(memberSignUpRequest);

        Booth booth = Booth.builder()
                .description("description")
                .detail("detail")
                .enabled(true)
                .location("location")
                .latitude(1.0)
                .longitude(1.0)
                .warning("warning")
                .category(BoothCategory.FOOD)
                .thumbnail("thumbnail")
                .name("booth")
                .festival(festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new))
                .build();
        booth.setMember(memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new));
        long boothId = boothRepository.save(booth).getId();

        MemberSignUpRequest memberSignUpRequest2 = new MemberSignUpRequest();
        memberSignUpRequest2.setEmail("test2@email.com");
        memberSignUpRequest2.setPassword("password");
        memberSignUpRequest2.setSchoolId(schoolId);
        memberSignUpRequest2.setPhoneNum("010-0000-0000");
        long newOwnerId = memberService.createMember(memberSignUpRequest2);

        adminService.changeBoothOwner(boothId, newOwnerId);

        assertEquals(Optional.of(newOwnerId), booth.getMember().getId());
    }

}
