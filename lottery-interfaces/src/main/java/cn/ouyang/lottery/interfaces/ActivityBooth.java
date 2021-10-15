package cn.ouyang.lottery.interfaces;

import cn.ouyang.lottery.common.Constants;
import cn.ouyang.lottery.common.Result;
import cn.ouyang.lottery.infrastructure.dao.IActivityDao;
import cn.ouyang.lottery.infrastructure.po.Activity;
import cn.ouyang.lottery.rpc.IActivityBooth;
import cn.ouyang.lottery.rpc.dto.ActivityDto;
import cn.ouyang.lottery.rpc.req.ActivityReq;
import cn.ouyang.lottery.rpc.response.ActivityRes;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class ActivityBooth implements IActivityBooth {

    @Resource
    private IActivityDao activityDao;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {
        Activity activity = activityDao.queryActivityById(req.getActivityId());

        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivityId(activity.getActivityId());
        activityDto.setActivityName(activity.getActivityName());
        activityDto.setActivityDesc(activity.getActivityDesc());
        activityDto.setBeginDateTime(activity.getBeginDateTime());
        activityDto.setEndDateTime(activity.getEndDateTime());
        activityDto.setStockCount(activity.getStockCount());
        activityDto.setTakeCount(activity.getTakeCount());

        return new ActivityRes(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
    }
}
