package com.chanokh.reggie.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chanokh.reggie.common.R;
import com.chanokh.reggie.dto.DishDto;
import com.chanokh.reggie.entity.Category;
import com.chanokh.reggie.entity.Dish;
import com.chanokh.reggie.entity.DishFlavor;
import com.chanokh.reggie.service.CategoryService;
import com.chanokh.reggie.service.DishFlavorService;
import com.chanokh.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.isNotEmpty(name),Dish::getName, name);
        qw.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, qw);
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((v)->{
            //原本dish 要转成dishDTO
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(v,dishDto);
            Category category = categoryService.getById(v.getCategoryId());
            if(category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success( dishDtoPage);
    }
    @GetMapping("/{id}")
    public R<DishDto> query(@PathVariable Long id) {
        //根据id查询原本菜品信息
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("更新成功");
    }
    @GetMapping("/list")
    //喵喵喵
    public R<List<DishDto>> list(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> DishDtoList = list.stream().map((v)->{
            //原本dish 要转成dishDTO
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(v,dishDto);
            Category category = categoryService.getById(v.getCategoryId());
            if(category != null) {
                dishDto.setCategoryName(category.getName());
            }
            //当前菜品的id
            Long dishId = v.getId();
            LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
            qw.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> list1 = dishFlavorService.list(qw);
            dishDto.setFlavors(list1);
            log.info("list1 : " + list1.toString());
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(DishDtoList);
    }
}
