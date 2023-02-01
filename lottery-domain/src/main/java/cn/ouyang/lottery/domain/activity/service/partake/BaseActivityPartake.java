package cn.ouyang.lottery.domain.activity.service.partake;

import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.common.Result;
import cn.ouyang.lottery.common.enums.Ids;
import cn.ouyang.lottery.domain.activity.model.req.PartakeReq;
import cn.ouyang.lottery.domain.activity.model.resp.PartakeResult;
import cn.ouyang.lottery.domain.activity.model.vo.ActivityBillVO;
import cn.ouyang.lottery.domain.activity.model.vo.UserTakeActivityVO;
import cn.ouyang.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 活动领取模板抽象类
 *
 */
public abstract class BaseActivityPartake extends ActivityPartakeSupport implements IActivityPartake{

    @Resource
    private Map<Ids, IIdGenerator> idGeneratorMap;

    /**
     * 活动领取的模板
     * @param req
     */
    @Override
    public PartakeResult doPartake(PartakeReq req) {
        //查询是否存在未执行抽奖领取活动单,如果有则可以直接返回
        UserTakeActivityVO userTakeActivityVO = this.queryNoConsumedTakeActivityOrder(req.getActivityId(), req.getuId());
        if (null != userTakeActivityVO){
            return buildPartakeResult(userTakeActivityVO.getStrategyId(),userTakeActivityVO.getTakeId());
        }
        //查询活动账单
        ActivityBillVO activityBillVO = super.queryActivityBill(req);
        //活动信息校验处理【活动库存、状态、日期、个人参与次数】
        Result checkResult = this.checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())){
            return new PartakeResult(checkResult.getCode(), checkResult.getInfo());
        }

        //扣减活动库存
        Result subtractionActivityResult = this.subtractionActivityStock(req);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionActivityResult.getCode())) {
            return new PartakeResult(subtractionActivityResult.getCode(), subtractionActivityResult.getInfo());
        }
        //领取活动信息【包括user_take_activity和user_take_activity_count两张表的修改】,使用了编程式事务
        long takeId = idGeneratorMap.get(Ids.SnowFlake).nextId();
        Result grabResult = this.grabActivity(req, activityBillVO,takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            return new PartakeResult(grabResult.getCode(), grabResult.getInfo());
        }
        //封装结果【返回策略id，用于继续完成抽奖步骤】
        return buildPartakeResult(activityBillVO.getStrategyId(),takeId);
    }

    /**
     * 封装结果【返回的策略ID，用于继续完成抽奖步骤】
     *
     * @param strategyId 策略ID
     * @param takeId     领取ID
     * @return 封装结果
     */
    private PartakeResult buildPartakeResult(Long strategyId, Long takeId) {
        PartakeResult partakeResult = new PartakeResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        return partakeResult;
    }

    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     *
     * @param activityId 活动ID
     * @param uId        用户ID
     * @return 领取单
     */
    protected abstract UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId,String uId);

    /**
     * 活动信息校验处理，包括活动库存、状态、日期、个人参与次数
     * @param req
     * @param bill
     * @return
     */
    protected abstract Result checkActivityBill(PartakeReq req,ActivityBillVO bill);

    /**
     * 扣减活动库存
     * @param req
     * @return
     */
    protected abstract Result subtractionActivityStock(PartakeReq req);

    /**
     * 领取活动
     * @param partakeReq
     * @param bill
     * @return
     */
    protected abstract Result grabActivity(PartakeReq partakeReq,ActivityBillVO bill, Long takeId);
}
