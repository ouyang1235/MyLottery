package cn.ouyang.lottery.common;

public enum DrawState {
    /**
     * 未中奖
     */
    FAIL(0,"未中奖"),

    /**
     * 已中奖
     */
    SUCCESS(1, "已中奖"),

    /**
     * 兜底奖
     */
    Cover(2,"兜底奖");

    private Integer code;
    private String info;

    DrawState(Integer code, String info) {
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
