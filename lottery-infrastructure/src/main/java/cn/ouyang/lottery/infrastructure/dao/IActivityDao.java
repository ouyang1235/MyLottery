package cn.ouyang.lottery.infrastructure.dao;

import cn.ouyang.lottery.infrastructure.po.ActivityDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IActivityDao {

    void insert(ActivityDO req);

    ActivityDO queryActivityById(Long activityId);

}
