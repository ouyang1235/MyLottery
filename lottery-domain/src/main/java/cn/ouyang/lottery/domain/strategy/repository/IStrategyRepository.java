package cn.ouyang.lottery.domain.strategy.repository;

import cn.ouyang.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.ouyang.lottery.infrastructure.po.Award;

public interface IStrategyRepository {

    StrategyRich queryStrategyRich(Long strategyId);

    Award queryAwardInfo(String awardId);

}
