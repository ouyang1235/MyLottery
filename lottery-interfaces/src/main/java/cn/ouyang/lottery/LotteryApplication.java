package cn.ouyang.lottery;

import cn.ouyang.lottery.domain.strategy.model.req.DrawReq;
import cn.ouyang.lottery.domain.strategy.service.draw.IDrawExec;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = {"cn.ouyang.lottery"})
@Configuration
//@EnableDubbo
public class LotteryApplication{

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }

}
