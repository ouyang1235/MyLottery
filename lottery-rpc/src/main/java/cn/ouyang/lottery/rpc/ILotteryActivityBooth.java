package cn.ouyang.lottery.rpc;

import cn.ouyang.lottery.rpc.req.DrawReq;
import cn.ouyang.lottery.rpc.req.QuantificationDrawReq;
import cn.ouyang.lottery.rpc.response.DrawResp;

/**
 * 抽奖活动展台接口
 */
public interface ILotteryActivityBooth {


    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawResp doDraw(DrawReq drawReq);

    /**
     * 量化人群抽奖
     * @param quantificationDrawReq 请求参数
     * @return                      抽奖结果
     */
    DrawResp doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);

}
