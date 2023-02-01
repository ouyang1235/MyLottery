package cn.ouyang.lottery.domain.activity.service.partake;

import cn.ouyang.lottery.common.Result;
import cn.ouyang.lottery.domain.activity.model.req.PartakeReq;
import cn.ouyang.lottery.domain.activity.model.resp.PartakeResult;
import cn.ouyang.lottery.domain.activity.model.vo.DrawOrderVO;

/**
 * 抽奖活动参与接口
 */
public interface IActivityPartake {

    /**
     * 参与活动
     */
    PartakeResult doPartake(PartakeReq req);

    /**
     * 保存奖品单
     * @param drawOrder 奖品单
     * @return          保存结果
     */
    Result recordDrawOrder(DrawOrderVO drawOrder);

}
