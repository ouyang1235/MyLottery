package cn.ouyang.lottery.infrastructure.repository;

import cn.ouyang.lottery.domain.award.repository.IOrderRepository;
import cn.ouyang.lottery.infrastructure.dao.IUserStrategyExportDao;
import cn.ouyang.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OrderRepository implements IOrderRepository {
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }
}
