package cn.ouyang.lottery.domain.rule.service.logic.impl;

import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

@Component
public class UserAgeFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatterReq) {
        return decisionMatterReq.getValMap().get("age").toString();
    }
}
