package cn.ouyang.lottery.common.enums;

public enum ActivityState {

    /** 1：编辑 */
    EDIT(1, "编辑"),
    /** 2：提审 */
    ARRAIGNMENT(2, "提审"),
    /** 3：撤审 */
    REVOKE(3, "撤审"),
    /** 4：通过 */
    PASS(4, "通过"),
    /** 5：运行(活动中) */
    DOING(5, "运行(活动中)"),
    /** 6：拒绝 */
    REFUSE(6, "拒绝"),
    /** 7：关闭 */
    CLOSE(7, "关闭"),
    /** 8：开启 */
    OPEN(8, "开启");

    private Integer code;
    private String info;

    ActivityState(Integer code, String info) {
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
