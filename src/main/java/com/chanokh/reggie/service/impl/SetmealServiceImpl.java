package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.common.CustomException;
import com.chanokh.reggie.dto.SetmealDto;
import com.chanokh.reggie.entity.Setmeal;
import com.chanokh.reggie.entity.SetmealDish;
import com.chanokh.reggie.service.SetmealDishService;
import com.chanokh.reggie.service.SetmealService;
import com.chanokh.reggie.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Ymtx
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-07-27 16:49:37
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    public SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveSetmeal(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map(v-> {
            v.setSetmealId( setmealDto.getId() + "");
            return v;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(list);
        return;
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐 是否停售
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        //能否删除
        qw.in(Setmeal::getId,ids).eq(Setmeal::getStatus, 1);

        Long count = this.count(qw);
        if(count > 0) {
            throw new CustomException("套餐正在售卖中，无法删除");
        }
        //删除套餐
        LambdaQueryWrapper<SetmealDish> sqw = new LambdaQueryWrapper<>();
        sqw.in(SetmealDish::getDishId, ids);
        this.removeByIds(ids);
        //删除关系
        setmealDishService.remove(sqw);
    }
}




