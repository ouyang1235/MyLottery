package cn.ouyang.lottery.domain.strategy.service.draw;


import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.common.DrawState;
import cn.ouyang.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.ouyang.lottery.domain.strategy.model.req.DrawReq;
import cn.ouyang.lottery.domain.strategy.model.res.DrawResult;
import cn.ouyang.lottery.domain.strategy.model.vo.AwardRateInfo;
import cn.ouyang.lottery.domain.strategy.model.vo.DrawAwardInfo;
import cn.ouyang.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import cn.ouyang.lottery.infrastructure.po.Award;
import cn.ouyang.lottery.infrastructure.po.Strategy;
import cn.ouyang.lottery.infrastructure.po.StrategyDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义抽奖过程，使用模板模式
 */
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec{

    private Logger logger = LoggerFactory.getLogger(AbstractDrawBase.class);

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        //1.获取抽奖策略聚合
        StrategyRich strategyRich = super.queryStrategyRich(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();

        //2.校验抽奖策略是否已经初始化到内存
        this.checkAndInitRateData(strategy.getStrategyId(),strategy.getStrategyMode(),strategyRich.getStrategyDetailList());

        //3.获取不在抽奖范围内的列表，包括：奖品库为空、风控策略、临时调整等
        List<String> excludeAwardIds = this.queryExcludeAwardIds(req.getStrategyId());

        //4.执行抽奖结果
        String awardId = this.drawAlgorithm(req.getStrategyId(), drawAlgorithmMap.get(strategy.getStrategyMode()), excludeAwardIds);

        //5.包装中奖结果
        return buildDrawResult(req.getuId(), req.getStrategyId(),awardId);
    }

    /**
     * 获取不在抽奖范围内的列表
     * @param strategyId
     * @return
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * 执行抽奖算法
     * @param strategyId
     * @param drawAlgorithm
     * @param excludeAwardIds
     * @return
     */
    protected abstract String drawAlgorithm(Long strategyId,IDrawAlgorithm drawAlgorithm,List<String> excludeAwardIds);

    private void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList){
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);

        boolean existRateTuple = drawAlgorithm.isExistRateTuple(strategyId);
        if (existRateTuple) return;

        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(),strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId,awardRateInfoList);

    }

    private DrawResult buildDrawResult(String uId,Long strategyId,String awardId){
        if (null == awardId){
            logger.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawResult(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }

        Award award = super.queryAwardInfoByAwardId(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo(award.getAwardId(), award.getAwardName());
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());

        return new DrawResult(uId, strategyId, DrawState.SUCCESS.getCode(), drawAwardInfo);
    }


}
