package UniFest.domain.home.dto.response;

import UniFest.domain.home.entity.HomeCard;
import UniFest.domain.home.entity.HomeTip;
import UniFest.domain.stamp.dto.response.StampRecordResponse;
import UniFest.domain.stamp.entity.StampInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class HomeInfoResponse {
    private List<HomeCard> homeCardList;
    private List<HomeTip> homeTipList;

    @Builder
    public HomeInfoResponse(List<HomeCard> homeCardList, List<HomeTip> homeTipList) {
        //TODO: 대충 여기에 Repo에서 getAllHomeCardList 부르면 되겠네
        this.homeCardList = homeCardList;
        this.homeTipList = homeTipList;
    }
}
