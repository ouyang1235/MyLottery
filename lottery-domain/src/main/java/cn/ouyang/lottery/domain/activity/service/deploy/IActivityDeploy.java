package cn.ouyang.lottery.domain.activity.service.deploy;

import cn.ouyang.lottery.domain.activity.model.req.ActivityConfigReq;

/**
 * 部署活动配置接口
 */
public interface IActivityDeploy {

    void createActivity(ActivityConfigReq req);

    void updateActivity(ActivityConfigReq req);

}
