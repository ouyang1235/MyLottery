package cn.ouyang.lottery.common.enums;

public enum TaskState {
    NO_USED(0, "未使用"),
    USED(1, "已使用");

    private Integer code;
    private String info;

    TaskState(Integer code, String info) {
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
