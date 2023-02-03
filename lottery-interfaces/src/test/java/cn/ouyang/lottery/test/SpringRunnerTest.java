package cn.ouyang.lottery.test;

import cn.ouyang.lottery.common.DrawState;
import cn.ouyang.lottery.domain.award.model.req.GoodsReq;
import cn.ouyang.lottery.domain.award.model.res.DistributionRes;
import cn.ouyang.lottery.domain.award.service.goods.IDistributionGoods;
import cn.ouyang.lottery.domain.award.service.goods.factory.DistributionGoodsFactory;
import cn.ouyang.lottery.domain.strategy.model.req.DrawReq;
import cn.ouyang.lottery.domain.strategy.model.res.DrawResult;
import cn.ouyang.lottery.domain.strategy.model.vo.AwardRateVO;
import cn.ouyang.lottery.domain.strategy.model.vo.DrawAwardVO;
import cn.ouyang.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import cn.ouyang.lottery.domain.strategy.service.draw.IDrawExec;
import cn.ouyang.lottery.infrastructure.dao.IActivityDao;
import cn.ouyang.lottery.infrastructure.po.Activity;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRunnerTest {


    private Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IDrawExec drawExec;


    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
    }

    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100001L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("xiaofuge");
        activityDao.insert(activity);
    }

    @Test
    public void test_select() {
        Activity activity = activityDao.queryActivityById(100001L);
        logger.info("测试结果：{}", JSON.toJSONString(activity));
    }

    @Resource(name = "singleRateRandomDrawAlgorithm")
    private IDrawAlgorithm randomDrawAlgorithm;

    @Before
    public void init() {
        // 奖品信息
        List<AwardRateVO> strategyList = new ArrayList<>();
        strategyList.add(new AwardRateVO("一等奖：IMac", new BigDecimal("0.05")));
        strategyList.add(new AwardRateVO("二等奖：iphone", new BigDecimal("0.15")));
        strategyList.add(new AwardRateVO("三等奖：ipad", new BigDecimal("0.20")));
        strategyList.add(new AwardRateVO("四等奖：AirPods", new BigDecimal("0.25")));
        strategyList.add(new AwardRateVO("五等奖：充电宝", new BigDecimal("0.35")));

        // 初始数据
        randomDrawAlgorithm.initRateTuple(100001L, strategyList);
    }

    @Test
    public void test_randomDrawAlgorithm() {

        List<String> excludeAwardIds = new ArrayList<>();
        excludeAwardIds.add("二等奖：iphone");
        excludeAwardIds.add("四等奖：AirPods");

        for (int i = 0; i < 20; i++) {
            System.out.println("中奖结果：" + randomDrawAlgorithm.randomDraw(100001L, excludeAwardIds));
        }

    }

    @Test
    public void test_award(){
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));

        Integer drawState = drawResult.getDrawState();
        if (DrawState.FAIL.getCode().equals(drawState)){
            logger.info("未中奖 DrawAwardInfo is null");
            return;
        }

        DrawAwardVO drawAwardVO = drawResult.getDrawAwardInfo();
        GoodsReq goodsReq = new GoodsReq(drawResult.getuId(), "2109313442431", drawAwardVO.getAwardId(), drawAwardVO.getAwardName(), drawAwardVO.getAwardContent());
        IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(drawAwardVO.getAwardType());
        DistributionRes distributionRes = distributionGoodsService.doDistribution(goodsReq);
        logger.info("测试结果：{}", JSON.toJSONString(distributionRes));
    }

}
