package cn.ouyang.lottery.domain.activity.model.aggregates;

import cn.ouyang.lottery.domain.activity.model.vo.ActivityVO;
import cn.ouyang.lottery.domain.activity.model.vo.AwardVO;
import cn.ouyang.lottery.domain.activity.model.vo.StrategyVO;

import java.util.List;

public class ActivityConfigRich {

    private ActivityVO activity;

    private StrategyVO strategy;

    private List<AwardVO> awardList;

    public ActivityConfigRich() {
    }

    public ActivityConfigRich(ActivityVO activity, StrategyVO strategy, List<AwardVO> awardList) {
        this.activity = activity;
        this.strategy = strategy;
        this.awardList = awardList;
    }

    public ActivityVO getActivity() {
        return activity;
    }

    public void setActivity(ActivityVO activity) {
        this.activity = activity;
    }

    public StrategyVO getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyVO strategy) {
        this.strategy = strategy;
    }

    public List<AwardVO> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<AwardVO> awardList) {
        this.awardList = awardList;
    }
}
