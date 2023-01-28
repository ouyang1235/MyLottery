package cn.ouyang.lottery.domain.award.service.goods.factory;

import cn.ouyang.lottery.common.enums.AwardType;
import cn.ouyang.lottery.domain.award.service.goods.IDistributionGoods;
import cn.ouyang.lottery.domain.award.service.goods.impl.CouponGoods;
import cn.ouyang.lottery.domain.award.service.goods.impl.DescGoods;
import cn.ouyang.lottery.domain.award.service.goods.impl.PhysicalGoods;
import cn.ouyang.lottery.domain.award.service.goods.impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 各类发奖奖品配置类
 */
public class GoodsConfig {

    protected static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private DescGoods descGoods;

    @Resource
    private RedeemCodeGoods redeemCodeGoods;

    @Resource
    private CouponGoods couponGoods;

    @Resource
    private PhysicalGoods physicalGoods;

    @PostConstruct
    public void init() {
        goodsMap.put(AwardType.DESC.getCode(), descGoods);
        goodsMap.put(AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
        goodsMap.put(AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(AwardType.PhysicalGoods.getCode(), physicalGoods);
    }

}
