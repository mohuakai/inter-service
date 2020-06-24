package com.code.jms.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author jms
 * @since 2020-04-01 18:42
 *
 * 用户信息接口: http://127.0.0.1:9080/user/getUserInfo?userId=123456
 * 用户余额接口: http://127.0.0.1:9080/money/getMoneyInfo?userId=123456
 */
@Service
public class UserServiceThread {
	@Autowired
	RemoteService remoteService;

	//待实现
	public String getUserInfo(String userId){
		long start = System.currentTimeMillis();

		/*new Thread(new Runnable() {
			@Override
			public void run() {
				String v1 = remoteService.getUserInfo(userId);
				JSONObject userInfo = JSONObject.parseObject(v1);// {1 2 3}
			}
		}).start();*/

		//选择Callable的一个原因是它有返回值
		Callable<JSONObject> userCall = new Callable<JSONObject>() {
			@Override
			public JSONObject call() throws Exception {
				String v1 = remoteService.getUserInfo(userId);
				JSONObject userInfo = JSONObject.parseObject(v1);// {1 2 3}
				return userInfo;
			}
		};

		Callable<JSONObject> moneyCall = new Callable<JSONObject>() {
			@Override
			public JSONObject call() throws Exception {
				String v2 = remoteService.getUserMoney(userId);//10S
				JSONObject moneyInfo = JSONObject.parseObject(v2);//{4 5 6}
				return moneyInfo;
			}
		};
		//FutureTask<JSONObject> userTask = new FutureTask<>(userCall);
		//自定义futureTask
		JamesFutureTask<JSONObject> userTask = new JamesFutureTask<>(userCall);
		JamesFutureTask<JSONObject> moneyTask = new JamesFutureTask<>(moneyCall);
		//FutureTask--------------run(userTask.userCall.call())----------------Runnable
		new Thread(userTask).start();
		new Thread(moneyTask).start();

		JSONObject result  = new JSONObject();
		try {
			result.putAll(userTask.get());//阻塞 监听userCall.call()有没有返回值
			result.putAll(moneyTask.get());//result={1 2 3 4 5 6}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("执行总时间为"+(System.currentTimeMillis() - start));
		//一起返回
		return result.toString();
	} 
}

