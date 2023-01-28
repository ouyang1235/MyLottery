package cn.ouyang.lottery.common.enums;

public enum AwardState {
    /**
     * 等待发奖
     */
    WAIT(0, "等待发奖"),

    /**
     * 发奖成功
     */
    SUCCESS(1, "发奖成功"),

    /**
     * 发奖失败
     */
    FAILURE(2, "发奖失败");

    private Integer code;
    private String info;

    AwardState(Integer code, String info) {
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
