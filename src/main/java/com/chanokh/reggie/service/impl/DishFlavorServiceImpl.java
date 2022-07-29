package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.DishFlavor;
import com.chanokh.reggie.service.DishFlavorService;
import com.chanokh.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-07-29 16:46:13
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




