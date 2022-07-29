package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.common.CustomException;
import com.chanokh.reggie.entity.Category;
import com.chanokh.reggie.entity.Dish;
import com.chanokh.reggie.entity.Setmeal;
import com.chanokh.reggie.service.CategoryService;
import com.chanokh.reggie.mapper.CategoryMapper;
import com.chanokh.reggie.service.DishService;
import com.chanokh.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-07-27 15:52:49
*/
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        //查询是否关联了菜品
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper();
        qw.eq(Dish::getCategoryId, id);
        Long count = dishService.count(qw);
        if(count > 0) {
            log.info("dish关联了" + count);

            //error
            throw new CustomException("当前分类下已关联菜品");
        }
        //查询是否关联了套餐
        LambdaQueryWrapper<Setmeal> setmealqw = new LambdaQueryWrapper();
        setmealqw.eq(Setmeal::getCategoryId,id);
        count = setmealService.count(setmealqw);
        if(count > 0) {
            //error
            log.info("setMeal关联了" + count);
            throw new CustomException("当前分类下已关联菜品");

        }
        //正常删除分类
        super.removeById(id);
    }
}




