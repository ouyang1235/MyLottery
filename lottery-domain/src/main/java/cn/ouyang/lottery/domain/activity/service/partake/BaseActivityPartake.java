package cn.ouyang.lottery.domain.activity.service.partake;

import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.common.Result;
import cn.ouyang.lottery.domain.activity.model.req.PartakeReq;
import cn.ouyang.lottery.domain.activity.model.resp.PartakeResult;
import cn.ouyang.lottery.domain.activity.model.vo.ActivityBillVO;

/**
 * 活动领取模板抽象类
 *
 */
public abstract class BaseActivityPartake extends ActivityPartakeSupport implements IActivityPartake{


    /**
     * 活动领取的模板
     * @param req
     */
    @Override
    public PartakeResult doPartake(PartakeReq req) {
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
        Result grabResult = this.grabActivity(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            return new PartakeResult(grabResult.getCode(), grabResult.getInfo());
        }
        //封装结果【返回策略id，用于继续完成抽奖步骤】
        PartakeResult partakeResult = new PartakeResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        partakeResult.setStrategyId(activityBillVO.getStrategyId());
        return partakeResult;
    }

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
    protected abstract Result grabActivity(PartakeReq partakeReq,ActivityBillVO bill);
}
