package cn.ouyang.lottery.domain.strategy.service.algorithm;

import cn.ouyang.lottery.domain.strategy.model.vo.AwardRateVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共用算法逻辑
 *
 */
public abstract class BaseAlgorithm implements IDrawAlgorithm{

    //斐波那契散列增量，逻辑：黄金分割点：(√5 - 1) / 2 = 0.6180339887，Math.pow(2, 32) * 0.6180339887 = 0x61c88647
    private final int HASH_INCREMENT = 0x61c88647;

    //数组初始化长度
    private final int RATE_TUPLE_LENGTH = 128;

    //String[]存放概率数组，数组中每个元素对应着奖品id
    protected Map<Long,String[]> rateTupleMap = new ConcurrentHashMap<>();

    //奖品概率信息列表
    protected Map<Long,List<AwardRateVO>> awardRateInfoMap = new ConcurrentHashMap<>();


    @Override
    public void initRateTuple(Long strategyId, List<AwardRateVO> awardRateVOList) {
        //保存奖品概率信息
        awardRateInfoMap.put(strategyId, awardRateVOList);

        String[] reteTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]);

        int cursorVal = 0;
        for (AwardRateVO awardRateVO : awardRateVOList) {
            int rateVal = awardRateVO.getAwardRate().multiply(new BigDecimal(100)).intValue();

            //循环填充概率范围
            for (int i = cursorVal + 1;i<=(cursorVal + rateVal);i++){
                reteTuple[hashIdx(i)] = awardRateVO.getAwardId();
            }

            cursorVal += rateVal;
        }
    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {
        return rateTupleMap.containsKey(strategyId);
    }

    /**
     * 斐波拉奇散列法
     * @return
     */
    protected  int hashIdx(int val){
        int hashCode = val* HASH_INCREMENT + HASH_INCREMENT;
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }
}
