package cn.ouayng.domain.strategy.service.algorithm;

import cn.ouayng.domain.strategy.model.vo.AwardRateInfo;

import java.util.List;

public abstract class BaseAlgorithm implements IDrawAlgorithm{

    @Override
    public void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList) {

    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {
        return false;
    }


}
