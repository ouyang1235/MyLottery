package cn.ouyang.lottery.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.ouyang.lottery.infrastructure.dao")
@Configuration
public class MybatisConfiguration {
}
