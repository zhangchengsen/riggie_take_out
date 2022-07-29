package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.Dish;
import com.chanokh.reggie.service.DishService;
import com.chanokh.reggie.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-07-27 16:49:25
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

}




