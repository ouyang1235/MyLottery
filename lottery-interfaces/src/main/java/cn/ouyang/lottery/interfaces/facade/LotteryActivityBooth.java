package cn.ouyang.lottery.interfaces.facade;

import cn.ouyang.lottery.application.process.IActivityProcess;
import cn.ouyang.lottery.application.process.req.DrawProcessReq;
import cn.ouyang.lottery.application.process.res.DrawProcessResult;
import cn.ouyang.lottery.application.process.res.RuleQuantificationCrowdResult;
import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.ouyang.lottery.domain.strategy.model.vo.DrawAwardVO;
import cn.ouyang.lottery.interfaces.assembler.IMapping;
import cn.ouyang.lottery.rpc.ILotteryActivityBooth;
import cn.ouyang.lottery.rpc.dto.AwardDTO;
import cn.ouyang.lottery.rpc.req.DrawReq;
import cn.ouyang.lottery.rpc.req.QuantificationDrawReq;
import cn.ouyang.lottery.rpc.response.DrawResp;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 抽奖活动展台
 */
@Controller
public class LotteryActivityBooth implements ILotteryActivityBooth {

    private Logger logger = LoggerFactory.getLogger(LotteryActivityBooth.class);

    @Resource
    private IActivityProcess activityProcess;

    @Resource
    private IMapping<DrawAwardVO, AwardDTO> awardMapping;


    @Override
    public DrawResp doDraw(DrawReq drawReq) {
        try{
            logger.info("抽奖，开始 uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());

            //1.执行抽奖
            DrawProcessResult result = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getuId(), drawReq.getActivityId()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(result.getCode())) {
                logger.error("抽奖，失败(抽奖过程异常) uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());
                return new DrawResp(result.getCode(), result.getInfo());
            }
            DrawAwardVO drawAwardInfo = result.getDrawAwardInfo();

            //2.数据转换
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardInfo);
            awardDTO.setActivityId(drawReq.getActivityId());
            //3.封装数据
            DrawResp drawResp = new DrawResp(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawResp.setAwardDTO(awardDTO);

            logger.info("抽奖，完成 uId：{} activityId：{} drawRes：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawResp));
            return drawResp;
        }catch (Exception e){
            logger.error("抽奖，失败 uId：{} activityId：{} reqJson：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
            return new DrawResp(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }

    @Override
    public DrawResp doQuantificationDraw(QuantificationDrawReq quantificationDrawReq) {
        try{
            //1.执行规则引擎
            logger.info("量化人群抽奖，开始 uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
            RuleQuantificationCrowdResult ruleQuantificationCrowdResult = activityProcess.doRuleQuantificationCrowd(new DecisionMatterReq(quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), quantificationDrawReq.getValMap()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(ruleQuantificationCrowdResult.getCode())) {
                logger.error("量化人群抽奖，失败(规则引擎执行异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawResp(ruleQuantificationCrowdResult.getCode(), ruleQuantificationCrowdResult.getInfo());
            }
            // 2. 执行抽奖
            Long activityId = ruleQuantificationCrowdResult.getActivityId();
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(quantificationDrawReq.getuId(), activityId));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                logger.error("量化人群抽奖，失败(抽奖过程异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawResp(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }

            // 3. 数据转换
            DrawAwardVO drawAwardVO = drawProcessResult.getDrawAwardInfo();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(activityId);

            // 4. 封装数据
            DrawResp drawRes = new DrawResp(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            logger.info("量化人群抽奖，完成 uId：{} treeId：{} drawRes：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(drawRes));
            return drawRes;
        } catch (Exception e) {
            logger.error("量化人群抽奖，失败 uId：{} treeId：{} reqJson：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(quantificationDrawReq), e);
            return new DrawResp(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }
}
