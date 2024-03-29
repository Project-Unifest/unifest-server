package UniFest.domain.menu.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.menu.entity.Menu;
import UniFest.domain.menu.repository.MenuRepository;
import UniFest.dto.request.menu.MenuCreateRequest;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.member.MemberEmailExistException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final BoothRepository boothRepository;
    private final MenuRepository menuRepository;
    public Long postMenu(MenuCreateRequest menuCreateRequest, Long boothId, MemberDetails memberDetails) {
        Booth booth = boothRepository.findById(boothId).orElseThrow(BoothNotFoundException::new);
        checkAuth(booth, memberDetails);
        Menu menu = Menu.builder()
                .name(menuCreateRequest.getName())
                .price(menuCreateRequest.getPrice())
                .imgUrl(menuCreateRequest.getImgUrl())
                .build();
        menu.setBooth(booth);

        return menuRepository.save(menu).getId();
    }

    public void checkAuth(Booth booth, MemberDetails memberDetails){
        if(!booth.getMember().getEmail().equals(memberDetails.getEmail())){
            throw new RuntimeException("권한이 없습니다.");
        }
    }


}
