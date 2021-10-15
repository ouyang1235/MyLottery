package cn.ouyang.lottery.common;

import java.io.Serializable;

/**
 * 统一返回对象中，Code码、Info描述
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 8067891535276492896L;
    private String code;
    private String info;

    public Result() {
    }

    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static Result buildResult(String code, String info){
        return new Result(code,info);
    }
    public static Result buildSuccessResult() {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult() {
        return new Result(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
