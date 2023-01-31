package cn.ouyang.lottery.domain.activity.service.partake;

import cn.ouyang.lottery.domain.activity.model.req.PartakeReq;
import cn.ouyang.lottery.domain.activity.model.vo.ActivityBillVO;
import cn.ouyang.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * 活动领取中一些通用的数据服务
 */
public class ActivityPartakeSupport {

    @Resource
    protected IActivityRepository activityRepository;


    protected ActivityBillVO queryActivityBill(PartakeReq req){
        return activityRepository.queryActivityBill(req);
    }

}
