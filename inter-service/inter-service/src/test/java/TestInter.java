
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import com.enjoy.james.service.JimsOrderService;
import com.enjoy.james.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.enjoy.james.TargetApplication;

@SpringBootTest(classes = TargetApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestInter {

	private static final int MAX_THREADS = 1000;//模拟1000用户请求 做并发调用远程接口

	//CountDownLatch
	private CountDownLatch cdl = new CountDownLatch(MAX_THREADS);//花名册

    @Autowired
	JimsOrderService jimsOrderService;

    //JEMTER LOAD
	//controller[http]      service[业务代码]   dao[压测：SQL]  性能指标  很低 加机器
	//性能优化：还原服务器原本应该有的性能！！！

	@Test
	public void testInterface() throws ExecutionException, InterruptedException {
		for(int i = 0; i< MAX_THREADS; i++) {
			Thread thread = new Thread(()->{

				Map<String, Object> result = null;//远程调订单查询接口
				try {
					cdl.countDown();//花名册减1  当前线程调用此方法，则计数减一
					cdl.await();//阻塞当前线程，直到计数器的值为0
					result = jimsOrderService.queryOrderInfo("xn1234567");
					//System.out.println(result);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
			thread.start();
		}
		Thread.sleep(2000);
	}
}



















