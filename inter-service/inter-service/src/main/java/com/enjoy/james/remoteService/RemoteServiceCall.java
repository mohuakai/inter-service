package com.enjoy.james.remoteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

//模拟远程接口服务类
@Service
public class RemoteServiceCall {
	//调用远程接口
	public HashMap<String,Object> queryOrderInfoByCode(String orderCode){
		try {
			Thread.sleep(50L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("orderMoney", new Random().nextInt(9999));
		hashMap.put("orderCode", orderCode);
		hashMap.put("orderTime", "20190131");
		hashMap.put("orderStatus", "finish");
		return hashMap;
	}
	

	public List<Map<String,Object>> queryOrderInfoByCodeBatch(List<Map<String, String>> params){
		List<Map<String,Object>> result = new ArrayList<>();
		//HTTP
		for(Map<String, String> param: params){
			HashMap<String,Object> hashMap = new HashMap<String,Object>();
			hashMap.put("orderMoney", new Random().nextInt(9999));
			hashMap.put("orderCode", param.get("orderCode"));
			hashMap.put("serialNo", param.get("serialNo"));
			hashMap.put("orderTime", "20190131");
			hashMap.put("orderStatus", "finish");
			result.add(hashMap);
		}
		return result;
	}
}
