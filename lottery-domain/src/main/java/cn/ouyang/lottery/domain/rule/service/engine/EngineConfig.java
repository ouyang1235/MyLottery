package cn.ouyang.lottery.domain.rule.service.engine;

import cn.ouyang.lottery.domain.rule.model.vo.TreeRootVO;
import cn.ouyang.lottery.domain.rule.service.logic.LogicFilter;
import cn.ouyang.lottery.domain.rule.service.logic.impl.UserAgeFilter;
import cn.ouyang.lottery.domain.rule.service.logic.impl.UserGenderFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 规则配置
 */
public class EngineConfig {

    protected static Map<String, LogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    @Resource
    private UserAgeFilter userAgeFilter;

    @Resource
    private UserGenderFilter userGenderFilter;

    @PostConstruct
    public void init(){
        logicFilterMap.put("userAge",userAgeFilter);
        logicFilterMap.put("userGender",userGenderFilter);
    }

}
