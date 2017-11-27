import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 89003522 on 2017/11/24.
 */
public class ThreadUnitTest {

    @Test
    public void countDownLatch() throws InterruptedException {
        /*线程同步计数器*/
        long start = System.currentTimeMillis();
        int threadNum = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0;i < threadNum;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(threadNum+1);
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    public void testThreadLocal() throws InterruptedException {
        /*创建线程局部变量，使之线程安全*/
        ThreadLocal<String> myLocalString = new ThreadLocal<>();
        myLocalString.set("www.ipav.vip");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("%s\t%d\n",myLocalString.get(),0);
                countDownLatch.countDown();
            }
        }).start();
        System.out.printf("%s\t%d\n",myLocalString.get(),1);
        countDownLatch.await();
    }

    @Test
    public void testInheritableThreadLocal() throws Exception {
        /*继承父线程的值*/
        ThreadLocal<String> myLocalString = new InheritableThreadLocal<>();
        myLocalString.set("www.ipav.vip");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("%s\t%d\n",myLocalString.get(),0);
                countDownLatch.countDown();
            }
        }).start();
        System.out.printf("%s\t%d\n",myLocalString.get(),1);
        countDownLatch.await();
    }

}
