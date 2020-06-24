package com.enjoy.james.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enjoy.james.service.OrderService;

/**
* 控制层：对外开放接口地址及路径   http://127.0.0.1:9080/order/getOrderInfo?orderCode=1
* @throws Exception  没毛病，
*/
@Controller
@RequestMapping("/order")
public class OrderController {
















	@Autowired
	OrderService orderService;
	
    @RequestMapping(value = "/getOrderInfo")
    @ResponseBody
    public Map<String,Object> getMoneyInfo(@RequestParam(required = false) String orderCode) throws InterruptedException, ExecutionException {
    	
        return orderService.queryOrderInfo(orderCode);
    }

}
