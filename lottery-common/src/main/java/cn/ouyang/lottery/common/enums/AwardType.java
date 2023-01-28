package cn.ouyang.lottery.common.enums;

public enum AwardType {
    /**
     * 文字描述
     */
    DESC(1, "文字描述"),
    /**
     * 兑换码
     */
    RedeemCodeGoods(2, "兑换码"),
    /**
     * 优惠券
     */
    CouponGoods(3, "优惠券"),
    /**
     * 实物奖品
     */
    PhysicalGoods(4, "实物奖品");

    private Integer code;
    private String info;

    AwardType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
