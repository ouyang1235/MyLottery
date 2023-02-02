package cn.ouyang.lottery.domain.rule.service.engine;

import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.domain.rule.model.aggregates.TreeRuleRich;
import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.rule.model.resp.EngineResult;
import cn.ouyang.lottery.domain.rule.model.vo.TreeNodeLineVO;
import cn.ouyang.lottery.domain.rule.model.vo.TreeNodeVO;
import cn.ouyang.lottery.domain.rule.model.vo.TreeRootVO;
import cn.ouyang.lottery.domain.rule.service.logic.LogicFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 规则引擎基础类
 */
public abstract class EngineBase extends EngineConfig implements EngineFilter{

    private Logger logger = LoggerFactory.getLogger(EngineBase.class);

    @Override
    public EngineResult process(DecisionMatterReq req) {
        throw new RuntimeException("未实现规则引擎");
    }

    protected TreeNodeVO engineDecisionMaker(TreeRuleRich treeRuleRich,DecisionMatterReq matter){
        TreeRootVO treeRoot = treeRuleRich.getTreeRoot();
        Map<Long, TreeNodeVO> treeNodeMap = treeRuleRich.getTreeNodeMap();

        //规则树根ID
        Long treeRootNodeId = treeRoot.getTreeRootNodeId();
        TreeNodeVO curNode = treeNodeMap.get(treeRootNodeId);

        //找最后的非叶子节点
        while (Constants.NodeType.STEM.equals(curNode.getNodeType())){
            String ruleKey = curNode.getRuleKey();
            List<TreeNodeLineVO> treeNodeLineInfoList = curNode.getTreeNodeLineInfoList();
            LogicFilter logicFilter = logicFilterMap.get(ruleKey);
            String curValue = logicFilter.matterValue(matter);
            Long nextId = logicFilter.filter(curValue, treeNodeLineInfoList);
            curNode = treeNodeMap.get(nextId);
            logger.info("决策树引擎=>{} userId：{} treeId：{} treeNode：{} ruleKey：{} matterValue：{}", treeRoot.getTreeName(), matter.getUserId(), matter.getTreeId(), curNode.getTreeNodeId(), ruleKey, curValue);
        }

        return curNode;

    }

}
