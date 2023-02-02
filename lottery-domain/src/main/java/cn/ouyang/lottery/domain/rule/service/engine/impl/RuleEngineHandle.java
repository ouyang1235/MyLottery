package cn.ouyang.lottery.domain.rule.service.engine.impl;

import cn.ouyang.lottery.domain.rule.model.aggregates.TreeRuleRich;
import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.rule.model.resp.EngineResult;
import cn.ouyang.lottery.domain.rule.model.vo.TreeNodeVO;
import cn.ouyang.lottery.domain.rule.repository.IRuleRepository;
import cn.ouyang.lottery.domain.rule.service.engine.EngineBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ruleEngineHandle")
public class RuleEngineHandle extends EngineBase {

    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineResult process(DecisionMatterReq req) {
        TreeRuleRich treeRuleRich = ruleRepository.queryTreeRuleRich(req.getTreeId());
        if(null == treeRuleRich){
            throw new RuntimeException("Tree Rule is null!");
        }
        TreeNodeVO treeNodeVO = engineDecisionMaker(treeRuleRich, req);
        return new EngineResult(req.getUserId(),req.getTreeId(),treeNodeVO.getTreeNodeId(),treeNodeVO.getNodeValue());
    }
}
