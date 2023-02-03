package cn.ouyang.lottery.domain.award.service.goods.impl;

import cn.ouyang.lottery.common.enums.AwardState;
import cn.ouyang.lottery.common.enums.AwardType;
import cn.ouyang.lottery.common.enums.GrantState;
import cn.ouyang.lottery.domain.award.model.req.GoodsReq;
import cn.ouyang.lottery.domain.award.model.res.DistributionRes;
import cn.ouyang.lottery.domain.award.service.goods.DistributionBase;
import cn.ouyang.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * 描述类商品
 */
@Component
public class DescGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), GrantState.COMPLETE.getCode());

        return new DistributionRes(req.getuId(), AwardState.SUCCESS.getCode(), AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return AwardType.DESC.getCode();
    }
}
