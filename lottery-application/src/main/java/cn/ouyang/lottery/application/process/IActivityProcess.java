package cn.ouyang.lottery.application.process;

import cn.ouyang.lottery.application.process.req.DrawProcessReq;
import cn.ouyang.lottery.application.process.res.DrawProcessResult;

/**
 * 活动抽奖流程编排接口
 */
public interface IActivityProcess {


    /**
     * 执行抽奖流程
     * @param req
     * @return
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

}
