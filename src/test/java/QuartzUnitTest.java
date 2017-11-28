import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import vip.ipav.unit.quartz.QuartzTest;

import java.util.Date;

/**
 * Created by 89003522 on 2017/11/27.
 */
public class QuartzUnitTest {

    @Test
    public void testCornTrigger() throws Exception{
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        scheduler=schedulerfactory.getScheduler();
        JobDetail job= JobBuilder.newJob(QuartzTest.class).withIdentity("job1", "group1").build();

        //使用cornTrigger规则  每两秒执行一次
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("cornTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? *"))
                .startNow().build();
        scheduler.scheduleJob(job, trigger);

        //开启任务
        scheduler.start();

        Thread.sleep(5000);
        scheduler.shutdown();
    }

    @Test
    public void testSimpleTrigger() throws Exception {
        SchedulerFactory schedulerfactory=new StdSchedulerFactory();
        Scheduler scheduler=null;
        scheduler = schedulerfactory.getScheduler(); //取得调度器

        JobDetail job = JobBuilder.newJob(QuartzTest.class).withIdentity("job2", "group2").build();

        //Milliseconds-毫秒;Seconds-秒；Minutes-分钟
        SimpleScheduleBuilder builder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(2)//每隔2秒执行1次，不会等待上一个执行完(除非只给一个活动线程
                                        // ---org.quartz.threadPool.threadCount = 1)
                .withRepeatCount(2);//总共执行3次,刚启动1次，再执行2次，共3次

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(
                "simpleTrigger", "group2").startNow().withSchedule(
                builder).build();

        scheduler.scheduleJob(job, trigger);//作业与调度规则绑定
        scheduler.start();//启动调度

        Thread.sleep(20000);
        scheduler.shutdown();
    }
}
