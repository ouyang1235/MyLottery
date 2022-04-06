package cn.ouyang.lottery.interfaces;

import cn.ouyang.lottery.interfaces.req.ActivityReq;
import cn.ouyang.lottery.interfaces.response.ActivityRes;

public interface IActivityBooth {

    ActivityRes queryActivityById(ActivityReq req);
}
