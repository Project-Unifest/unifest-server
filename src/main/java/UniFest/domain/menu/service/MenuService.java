package UniFest.domain.menu.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.menu.entity.Menu;
import UniFest.domain.menu.entity.MenuStatus;
import UniFest.domain.menu.repository.MenuRepository;
import UniFest.dto.request.menu.MenuPatchRequest;
import UniFest.exception.menu.MenuNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final CacheManager cacheManager;

    public void patchMenu(Long menuId, MemberDetails memberDetails, MenuPatchRequest menuPatchRequest){
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        Booth findBooth = findMenu.getBooth();

        Optional.ofNullable(menuPatchRequest.getName())
                .ifPresent(name -> findMenu.updateName(name));
        Optional.ofNullable(menuPatchRequest.getPrice())
                .ifPresent(price -> findMenu.updatePrice(price));
        Optional.ofNullable(menuPatchRequest.getImgUrl())
                .ifPresent(imgUrl -> findMenu.updateImgUrl(imgUrl));

        cacheManager.getCache("BoothInfo").evict(findBooth.getId());
        checkBoothAuth(findBooth, memberDetails);

        menuRepository.save(findMenu);
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

    public void changeMenuStatus(MemberDetails memberDetails, Long menuId, MenuStatus menuStatus){
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        Booth findBooth = findMenu.getBooth();
        cacheManager.getCache("BoothInfo").evict(findMenu.getId());
        checkBoothAuth(findBooth, memberDetails);
        findMenu.updateMenuStatus(menuStatus);
    }
}
