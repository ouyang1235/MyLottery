package cn.ouyang.lottery.application.process.impl;

import cn.ouyang.lottery.application.process.IActivityProcess;
import cn.ouyang.lottery.application.process.req.DrawProcessReq;
import cn.ouyang.lottery.application.process.res.DrawProcessResult;
import cn.ouyang.lottery.application.process.res.RuleQuantificationCrowdResult;
import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.common.enums.GrantState;
import cn.ouyang.lottery.common.enums.Ids;
import cn.ouyang.lottery.domain.activity.model.req.PartakeReq;
import cn.ouyang.lottery.domain.activity.model.resp.PartakeResult;
import cn.ouyang.lottery.domain.activity.model.vo.DrawOrderVO;
import cn.ouyang.lottery.domain.activity.service.partake.IActivityPartake;
import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.rule.model.resp.EngineResult;
import cn.ouyang.lottery.domain.rule.service.engine.EngineFilter;
import cn.ouyang.lottery.domain.strategy.model.req.DrawReq;
import cn.ouyang.lottery.domain.strategy.model.res.DrawResult;
import cn.ouyang.lottery.domain.strategy.model.vo.DrawAwardVO;
import cn.ouyang.lottery.domain.strategy.service.draw.IDrawExec;
import cn.ouyang.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ActivityProcessImpl implements IActivityProcess {

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private IDrawExec drawExec;

    @Resource(name = "ruleEngineHandle")
    private EngineFilter engineFilter;

    @Resource
    private Map<Ids, IIdGenerator> idGeneratorMap;

    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {
        //1.领取活动
        PartakeResult partakeResult = activityPartake.doPartake(new PartakeReq(req.getuId(), req.getActivityId()));
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(partakeResult.getCode())) {
            return new DrawProcessResult(partakeResult.getCode(), partakeResult.getInfo());
        }
        Long strategyId = partakeResult.getStrategyId();
        Long takeId = partakeResult.getTakeId();
        //2.执行抽奖
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq(req.getuId(), strategyId, String.valueOf(takeId)));
        if (Constants.DrawState.FAIL.getCode().equals(drawResult.getDrawState())) {
            return new DrawProcessResult(Constants.ResponseCode.LOSING_DRAW.getCode(), Constants.ResponseCode.LOSING_DRAW.getInfo());
        }
        //3.结果落库
        DrawAwardVO drawAwardVO = drawResult.getDrawAwardInfo();
        activityPartake.recordDrawOrder(buildDrawOrderVO(req,strategyId,takeId, drawAwardVO));
        //4.发送MQ，触发发奖流程 todo

        //5.返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardVO);
    }

    @Override
    public RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req) {
        //量化决策
        EngineResult engineResult = engineFilter.process(req);
        if (!engineResult.isSuccess()){
            return new RuleQuantificationCrowdResult(Constants.ResponseCode.RULE_ERR.getCode(),Constants.ResponseCode.RULE_ERR.getInfo());
        }
        // 2. 封装结果
        RuleQuantificationCrowdResult ruleQuantificationCrowdResult = new RuleQuantificationCrowdResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        ruleQuantificationCrowdResult.setActivityId(Long.valueOf(engineResult.getNodeValue()));

        return ruleQuantificationCrowdResult;
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardVO) {
        long orderId = idGeneratorMap.get(Ids.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardVO.getStrategyMode());
        drawOrderVO.setGrantType(drawAwardVO.getGrantType());
        drawOrderVO.setGrantDate(drawAwardVO.getGrantDate());
        drawOrderVO.setGrantState(GrantState.INIT.getCode());
        drawOrderVO.setAwardId(drawAwardVO.getAwardId());
        drawOrderVO.setAwardType(drawAwardVO.getAwardType());
        drawOrderVO.setAwardName(drawAwardVO.getAwardName());
        drawOrderVO.setAwardContent(drawAwardVO.getAwardContent());
        return drawOrderVO;
    }

}
