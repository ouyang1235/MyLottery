package cn.ouyang.lottery.rpc;

import cn.ouyang.lottery.rpc.req.ActivityReq;
import cn.ouyang.lottery.rpc.response.ActivityRes;

public interface IActivityBooth {

    ActivityRes queryActivityById(ActivityReq req);
}
