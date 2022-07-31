package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.Orders;
import com.chanokh.reggie.service.OrdersService;
import com.chanokh.reggie.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-07-31 22:02:53
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




