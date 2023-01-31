package cn.ouyang.lottery.domain.activity.service.partake;

import cn.ouyang.lottery.domain.activity.model.req.PartakeReq;
import cn.ouyang.lottery.domain.activity.model.resp.PartakeResult;

/**
 * 抽奖活动参与接口
 */
public interface IActivityPartake {

    /**
     * 参与活动
     */
    PartakeResult doPartake(PartakeReq req);

}
