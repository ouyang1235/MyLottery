package cn.ouyang.lottery.domain.strategy.service.algorithm.impl;

import cn.ouyang.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

/**
 * 单项概率
 */
@Component("singleRateRandomDrawAlgorithm")
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        //获取元组
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        assert rateTuple != null;


        //随机索引
        int randomVal = new SecureRandom().nextInt(100) + 1;
        int idx = super.hashIdx(randomVal);

        String awardId = rateTuple[idx];
        if (excludeAwardIds.contains(awardId)) return "未中奖";

        return awardId;
    }
}
