package cn.ouyang.lottery.common.enums;

public enum MQState {
    INIT(0, "初始"),
    COMPLETE(1, "完成"),
    FAIL(2, "失败");

    private Integer code;
    private String info;

    MQState(Integer code, String info) {
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
