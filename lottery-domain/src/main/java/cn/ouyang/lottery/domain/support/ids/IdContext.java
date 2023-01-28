package cn.ouyang.lottery.domain.support.ids;

import cn.ouyang.lottery.common.enums.Ids;
import cn.ouyang.lottery.domain.support.ids.policy.RandomNumeric;
import cn.ouyang.lottery.domain.support.ids.policy.ShortCode;
import cn.ouyang.lottery.domain.support.ids.policy.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * id策略模式上下文配置
 */
@Configuration
public class IdContext {

    @Bean
    public Map<Ids,IIdGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumeric randomNumeric){
        HashMap<Ids, IIdGenerator> idGeneratorMap = new HashMap<>(8);
        idGeneratorMap.put(Ids.SnowFlake, snowFlake);
        idGeneratorMap.put(Ids.ShortCode, shortCode);
        idGeneratorMap.put(Ids.RandomNumeric, randomNumeric);
        return idGeneratorMap;
    }

}
