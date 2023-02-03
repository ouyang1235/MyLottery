package cn.ouyang.lottery.application.process;

import cn.ouyang.lottery.application.process.req.DrawProcessReq;
import cn.ouyang.lottery.application.process.res.DrawProcessResult;
import cn.ouyang.lottery.application.process.res.RuleQuantificationCrowdResult;
import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;

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


    /**
     * 规则量化人群，返回可参与的活动ID
     * @param req   规则请求
     * @return      量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req);


}
