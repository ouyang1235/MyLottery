package cn.ouyang.lottery.domain.activity.model.resp;

import cn.ouyang.lottery.common.Result;

/**
 * 活动参与结果
 */
public class PartakeResult extends Result {

    /**
     * 策略id
     */
    private Long strategyId;

    public PartakeResult(String code, String info) {
        super(code, info);
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

}
