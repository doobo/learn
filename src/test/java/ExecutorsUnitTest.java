import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by 89003522 on 2017/11/27.
 */
public class ExecutorsUnitTest {

    @Test
    public void testExecutorCache() throws InterruptedException {
        /*缓存型线程池,线程复用，常用于短时间的线程*/
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch  = new CountDownLatch(20);
        for(int i = 0;i<20;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"线程被调用了！");
                    countDownLatch.countDown();
                }
            });
        }
        executorService.shutdown();
        countDownLatch.await();
    }

    @Test
    public void testExecutorFixed() throws InterruptedException {
        /*固定数目的线程池,只会有3个线程活动，常用于服务端*/
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for(int i = 0 ;i<20;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"线程被调用了！");
                    countDownLatch.countDown();
                }
            });
        }
        executorService.shutdown();
        countDownLatch.await();
    }

    @Test
    public void testExecutorSingle() throws InterruptedException {
        /*单例线程池,只会有1个线程活动,常用于作业，顺序执行*/
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for(int i = 0 ;i<20;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"线程被调用了！");
                    countDownLatch.countDown();
                }
            });
        }
        executorService.shutdown();
        countDownLatch.await();
    }

    @Test
    public void testScheduleAtFixedRate() throws InterruptedException {
        /*现在开始2秒钟之后,每隔3秒,执行一次线程,不会等待上一线程结束*/
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.printf("%s\t%s\n",time.format(new Date()),Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        },2,3, TimeUnit.SECONDS);
        countDownLatch.await();
        executorService.shutdown();
    }

    @Test
    public void testScheduleWithFixedRate() throws InterruptedException {
        /*现在开始2秒钟之后,上一个线程结束3秒后，再执行一次线程*/
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.printf("%s\t%s\n",time.format(new Date()),Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        },2,3, TimeUnit.SECONDS);
        countDownLatch.await();
        executorService.shutdown();
    }

    @Test
    public void testExecutorCallable(){
        /*带返回值的多线程池*/
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        //创建10个任务并执行
        for (int i = 0; i < 10; i++){
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return Thread.currentThread().getName();
                }
            });
            //将任务执行结果存储到List中
            resultList.add(future);
        }
        for (Future<String> fs : resultList){
            try{
                while(!fs.isDone());//Future返回如果没有完成，则一直循环等待，直到Future返回完成
                System.out.println(fs.get());     //打印各个线程（任务）执行的结果
            }catch(InterruptedException e){
                e.printStackTrace();
            }catch(ExecutionException e){
                e.printStackTrace();
            }finally{
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务
                executorService.shutdown();
            }
        }
    }




}
