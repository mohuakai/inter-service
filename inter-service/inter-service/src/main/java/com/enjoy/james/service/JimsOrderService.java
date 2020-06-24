package com.enjoy.james.service;

import com.enjoy.james.remoteService.RemoteServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

@Service
public class JimsOrderService {
	@Autowired
	RemoteServiceCall remoteServiceCall;
	class  Request{
		String orderCode;
		String serialNo;
		CompletableFuture<Map<String, Object>> future;
	}

	LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

	//线程调用方法
	//线程1－士兵1－－－orderCode
	public Map<String, Object> queryOrderInfo(String orderCode) throws InterruptedException, ExecutionException {
		//orderCode;
		String serialNo = UUID.randomUUID().toString();
		Request request = new Request();
		request.orderCode = orderCode;
		request.serialNo = serialNo;

		CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
		request.future = future;
		//存放到队列方便获取，批量查询操作
		queue.add(request);
		//调用远程接口
		//return remoteServiceCall.queryOrderInfoByCode(orderCode);//调订单系统提供的订单接口 dubbo http
		return future.get();//不断监听自己的线程有没有返回值： 阻塞
	}

	//用在@service修饰的类中,tomcat启动过程初始化我们的web系统，执行init，
	//相当于servlet---init()
	@PostConstruct
	public void init(){
		ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
		scheduled.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				int size = queue.size();
				if (size == 0){
					return;
				}
				//拼装批量的请求参数
				//request.future在其他地方用到，要保存下来
				ArrayList<Request> requests = new ArrayList<>();
				List<Map<String, String>> params = new ArrayList<>();
				for (int i = 0; i <size ; i++) {
					Request request = queue.poll();
					requests.add(request);
					//拼装批量的请求参数
					Map<String, String> map = new HashMap<>();
					map.put("serialNo",request.serialNo);
					map.put("orderCode",request.orderCode);
					params.add(map);

				}
				List<Map<String, Object>> responses = remoteServiceCall.queryOrderInfoByCodeBatch(params);
				for (Request request : requests) {
					String serialNo = request.serialNo;
					for (Map<String, Object> respons : responses) {
						if (serialNo.equals(respons.get("serilNo").toString())){
							//通知线程，就是上面的future.get()方法
							request.future.complete(respons);//括号内的格式是Map<String, Object>
							break;
						}
					}
				}

			}

		},0,10,TimeUnit.MILLISECONDS);
		//initialDelay:0代表不等待直接执行；period:10代表间隔10毫秒执行该方法
	}
}
