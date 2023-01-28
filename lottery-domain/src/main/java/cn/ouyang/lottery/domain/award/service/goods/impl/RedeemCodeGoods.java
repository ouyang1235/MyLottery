package cn.ouyang.lottery.domain.award.service.goods.impl;

import cn.ouyang.lottery.common.enums.AwardState;
import cn.ouyang.lottery.common.enums.AwardType;
import cn.ouyang.lottery.domain.award.model.req.GoodsReq;
import cn.ouyang.lottery.domain.award.model.res.DistributionRes;
import cn.ouyang.lottery.domain.award.service.goods.DistributionBase;
import cn.ouyang.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * 兑换码类商品
 */
@Component
public class RedeemCodeGoods extends DistributionBase implements IDistributionGoods {


    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        // 模拟调用兑换码
        logger.info("模拟调用兑换码 uId：{} awardContent：{}", req.getuId(), req.getAwardContent());

        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), AwardState.SUCCESS.getCode(), AwardState.SUCCESS.getInfo());

        return new DistributionRes(req.getuId(), AwardState.SUCCESS.getCode(), AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return AwardType.RedeemCodeGoods.getCode();
    }
}