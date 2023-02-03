package cn.ouyang.lottery.domain.award.service.goods.impl;

import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.common.enums.AwardState;
import cn.ouyang.lottery.common.enums.AwardType;
import cn.ouyang.lottery.common.enums.GrantState;
import cn.ouyang.lottery.domain.award.model.req.GoodsReq;
import cn.ouyang.lottery.domain.award.model.res.DistributionRes;
import cn.ouyang.lottery.domain.award.service.goods.DistributionBase;
import cn.ouyang.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * 优惠券商品
 */
@Component
public class CouponGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        // 模拟调用优惠券发放接口
        logger.info("模拟调用优惠券发放接口 uId：{} awardContent：{}", req.getuId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), GrantState.COMPLETE.getCode());

        return new DistributionRes(req.getuId(), AwardState.SUCCESS.getCode(), AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return AwardType.CouponGoods.getCode();
    }
}
