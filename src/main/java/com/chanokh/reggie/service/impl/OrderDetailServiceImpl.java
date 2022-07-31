package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.OrderDetail;
import com.chanokh.reggie.service.OrderDetailService;
import com.chanokh.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-07-31 22:05:31
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




