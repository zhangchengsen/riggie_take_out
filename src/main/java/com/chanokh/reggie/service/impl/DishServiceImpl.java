package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.dto.DishDto;
import com.chanokh.reggie.entity.Dish;
import com.chanokh.reggie.entity.DishFlavor;
import com.chanokh.reggie.service.DishFlavorService;
import com.chanokh.reggie.service.DishService;
import com.chanokh.reggie.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Ymtx
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-07-27 16:49:25
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional  //开启事务 需要在启动类中添加@EnableTransactionManagement
    public void saveWithFlavor(DishDto dishDto) {
        //新增菜品 同时插入口味数据 操作两张表 dish dish_flavor
        //因为继承 所以可以直接save
        this.save(dishDto);
        Long dish_id = dishDto.getId();
        List<DishFlavor> flavorList = dishDto.getFlavors();
        //通过流加工
        flavorList = flavorList.stream().map((item) -> {
            item.setDishId(dish_id);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavorList);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish  dish =this.getById(id);
        DishDto dto = new DishDto();
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(qw);
        BeanUtils.copyProperties(dish,dto);
        dto.setFlavors(flavors);
        return dto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
         this.updateById(dishDto);
         //清理当前菜品对应口味数据 dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(qw);
        //添加提交过来的口味操作
        List<DishFlavor> list = dishDto.getFlavors();
        list = list.stream().map(v->{
            v.setDishId(dishDto.getId());
            return v;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(list);


    }
}




