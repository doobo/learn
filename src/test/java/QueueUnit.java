import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by 89003522 on 2017/12/12.
 * 队列的简单使用
 */
public class QueueUnit {

    @Test
    public void common(){
        Queue<String> queue = new LinkedList<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        //提前队前元素a,并从队列删除
        System.out.println(queue.poll());
        queue.offer("f");
        //提取队前元素，不删除
        System.out.println(queue.peek());
        System.out.println(queue.element());

        System.out.println("输出剩于的参数……");
        while (true){
            //防止调用queue.isEmpty()时，queue参数被改变
            synchronized (queue){
                if(!queue.isEmpty()){
                    System.out.println(queue.poll());
                }else{
                    break;
                }
            }
        }
    }

    @Test
    public void blocking(){
        Queue<String> queue = new LinkedList<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");

        
    }
}
