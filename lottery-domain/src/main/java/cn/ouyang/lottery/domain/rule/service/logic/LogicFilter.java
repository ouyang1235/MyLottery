package cn.ouyang.lottery.domain.rule.service.logic;

import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * 规则引擎过滤器接口
 * 它的作用：根据请求实体，获取所需要的匹配参数字段，并且根据该参数从给出的树茎组合选择正确的，返回下一个树节点
 */
public interface LogicFilter {

    /**
     * 逻辑决策器
     * @param matterValue
     * @param treeNodeLineVOList
     * @return
     */
    Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineVOList);

    /**
     * 获取决策值
     * @param decisionMatterReq
     * @return
     */
    String matterValue(DecisionMatterReq decisionMatterReq);


}
