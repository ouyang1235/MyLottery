package cn.ouyang.lottery.domain.rule.service.logic;

import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * 规则基础抽象类
 * 负责选择出正确的树茎和对应的树节点
 */
public abstract class BaseLogic implements LogicFilter{

    @Override
    public Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineVOList) {
        for (TreeNodeLineVO nodeLineVO : treeNodeLineVOList) {
            if (decisionLogic(matterValue,nodeLineVO)){
                return nodeLineVO.getNodeIdTo();
            }
        }
        return Constants.Global.TREE_NULL_NODE;
    }


    private boolean decisionLogic(String matterValue,TreeNodeLineVO nodeLine){
        switch (nodeLine.getRuleLimitType()) {
            case Constants.RuleLimitType.EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.GT:
                return Double.parseDouble(matterValue) > Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.LT:
                return Double.parseDouble(matterValue) < Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.GE:
                return Double.parseDouble(matterValue) >= Double.parseDouble(nodeLine.getRuleLimitValue());
            case Constants.RuleLimitType.LE:
                return Double.parseDouble(matterValue) <= Double.parseDouble(nodeLine.getRuleLimitValue());
            default:
                return false;
        }
    }


}
