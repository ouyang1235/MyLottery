package cn.ouayng.domain.strategy.repository;

import cn.ouayng.domain.strategy.model.aggregates.StrategyRich;
import cn.ouayng.domain.strategy.model.entity.Award;

public interface IStrategyRepository {

   StrategyRich queryStrategyRich (Long strategyId);

   Award queryAwardInfo(String awardId);

}
