package cn.ouyang.lottery.test.application;

import cn.ouyang.lottery.application.mq.KafkaProducer;
import cn.ouyang.lottery.interfaces.LotteryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class KafkaProducerTest {

    private Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    public void test_send() throws InterruptedException {
        // 循环发送消息
        while (true) {
            kafkaProducer.send("你好，我是Lottery 001");
            Thread.sleep(3500);
        }
    }
}
