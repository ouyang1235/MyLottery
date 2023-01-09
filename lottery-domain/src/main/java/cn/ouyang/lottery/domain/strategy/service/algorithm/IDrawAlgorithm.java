package cn.ouyang.lottery.domain.strategy.service.algorithm;

import cn.ouyang.lottery.domain.strategy.model.vo.AwardRateInfo;

import java.util.List;

public interface IDrawAlgorithm {

    /**
     * SecureRandom 生成随机数，索引到对应的奖品信息返回结果
     *
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除掉已经不能作为抽奖的奖品ID，留给风控和空库存使用
     * @return 中奖结果
     */
    String randomDraw(Long strategyId, List<String> excludeAwardIds);


    /**
     * 程序启动时初始化概率元组
     */
    void initRateTuple(Long strategyId,List<AwardRateInfo> awardRateInfoList);

    /**
     * 判断该策略是否已经初始化
     * @param strategyId
     * @return
     */
    boolean isExistRateTuple(Long strategyId);

}
