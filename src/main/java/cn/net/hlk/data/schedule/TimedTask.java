package cn.net.hlk.data.schedule;

import cn.net.hlk.data.mapper.OnsitePunishmentMapper;
import cn.net.hlk.data.service.OnsitePunishmentService;
import cn.net.hlk.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimedTask {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${scheduled.enable}")
    private String scheduledEnable;


    //    @Scheduled(cron = "0 01 00 * * ?")
    @Scheduled(fixedRate = 1000 * 60 * 60 * 5)
    public void alarmcreate() {}

    /**
     * 对非现场处罚添加数据失败时每隔5小时插入一次
     *
     * @param
     * @return void
     * @author gaoxipeng
     * @created 2019/4/15
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 5)
    public void OffOnsitePunishment() {}

}
