package cn.ouyang.lottery.domain.strategy.service.draw;

import cn.ouyang.lottery.domain.strategy.model.req.DrawReq;
import cn.ouyang.lottery.domain.strategy.model.res.DrawResult;

public interface IDrawExec {

    DrawResult doDrawExec(DrawReq req);

}
