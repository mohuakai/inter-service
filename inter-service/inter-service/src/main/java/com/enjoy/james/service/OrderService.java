package com.enjoy.james.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoy.james.remoteService.RemoteServiceCall;

@Service
public class OrderService {
	@Autowired
	RemoteServiceCall remoteServiceCall;

	public Map<String, Object> queryOrderInfo(String orderCode) throws InterruptedException, ExecutionException {
		//调用远程接口
		return remoteServiceCall.queryOrderInfoByCode(orderCode);//调订单系统提供的订单接口 dubbo http
	}
}
