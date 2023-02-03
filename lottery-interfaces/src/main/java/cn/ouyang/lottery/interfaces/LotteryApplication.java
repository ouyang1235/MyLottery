package cn.ouyang.lottery.interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"cn.ouyang.lottery"})
@Configuration
//@EnableDubbo
public class LotteryApplication{

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }

}
