package cn.ouyang.lottery.domain.rule.repository;

import cn.ouyang.lottery.domain.rule.model.aggregates.TreeRuleRich;

/**
 * 规则信息仓储服务接口
 */
public interface IRuleRepository {

    /**
     * 查询规则决策树配置
     * @return
     */
    TreeRuleRich queryTreeRuleRich(Long treeId);

}
