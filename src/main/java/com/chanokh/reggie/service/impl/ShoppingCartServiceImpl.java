package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.ShoppingCart;
import com.chanokh.reggie.service.ShoppingCartService;
import com.chanokh.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-07-31 20:42:45
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




