package UniFest.domain.menu.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.menu.entity.Menu;
import UniFest.domain.menu.repository.MenuRepository;
import UniFest.dto.request.menu.MenuCreateRequest;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.member.MemberEmailExistException;
import UniFest.exception.menu.MenuNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final BoothRepository boothRepository;
    private final MenuRepository menuRepository;
    private final CacheManager cacheManager;


    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long postMenu(MenuCreateRequest menuCreateRequest, Long boothId, MemberDetails memberDetails) {
        Booth findbooth = boothRepository.findById(boothId).orElseThrow(BoothNotFoundException::new);
        checkBoothAuth(findbooth, memberDetails);
        Menu menu = Menu.builder()
                .name(menuCreateRequest.getName())
                .price(menuCreateRequest.getPrice())
                .imgUrl(menuCreateRequest.getImgUrl())
                .build();
        menu.setBooth(findbooth);

        return menuRepository.save(menu).getId();
    }

    public void deleteMenu(Long menuId, MemberDetails memberDetails) {
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        Booth findbooth = findMenu.getBooth();
        cacheManager.getCache("BoothInfo").evict(findbooth.getId());
        checkBoothAuth(findbooth, memberDetails);
        menuRepository.delete(findMenu);
    }


    public void checkBoothAuth(Booth booth, MemberDetails memberDetails){
        if(!booth.getMember().getEmail().equals(memberDetails.getEmail())){
            throw new RuntimeException("권한이 없습니다.");
        }
    }

}
