package cn.ouyang.lottery.domain.rule.service.engine;

import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.rule.model.resp.EngineResult;

/**
 * 规则过滤器引擎
 */
public interface EngineFilter {

    /**
     * 通过规则引擎获取结果
     * @param req
     * @return
     */
    EngineResult process(DecisionMatterReq req);

}
