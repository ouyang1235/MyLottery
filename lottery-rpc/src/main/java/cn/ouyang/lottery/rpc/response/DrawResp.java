package cn.ouyang.lottery.rpc.response;

import cn.ouyang.lottery.common.Result;
import cn.ouyang.lottery.rpc.dto.AwardDTO;

import java.io.Serializable;

public class DrawResp extends Result implements Serializable {

    private AwardDTO awardDTO;

    public DrawResp (String code, String info) {
        super(code, info);
    }

    public AwardDTO getAwardDTO() {
        return awardDTO;
    }

    public void setAwardDTO(AwardDTO awardDTO) {
        this.awardDTO = awardDTO;
    }


}
