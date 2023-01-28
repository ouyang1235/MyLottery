package cn.ouyang.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.ouyang.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 雪花法
 */
@Component
public class SnowFlake implements IIdGenerator {

    /**
     * 借用hutool的工具
     */
    private Snowflake snowflake;

    @PostConstruct
    public void init(){
        //0 ~ 31位，可采用配置的方式使用
        long workerId;
        try{
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        }catch (Exception e){
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenter = 1L;
        snowflake = IdUtil.createSnowflake(workerId,dataCenter);

    }

    @Override
    public synchronized long nextId() {
        return snowflake.nextId();
    }
}
