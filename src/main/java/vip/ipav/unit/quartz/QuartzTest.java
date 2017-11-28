package vip.ipav.unit.quartz;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest implements Job {

    @Override
    //该方法实现需要执行的任务
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.printf("%s\t%s\n",new Date().toString(),Thread.currentThread().getName());
        writeFileToEnd(new Date().toString()+"\r\n","D:/test.txt");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileToEnd(String url,String filename){
        try{
            FileWriter writer = new FileWriter(filename, true);
            writer.write(url);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
