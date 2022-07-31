package com.chanokh.reggie.service;

import com.chanokh.reggie.dto.SetmealDto;
import com.chanokh.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Ymtx
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-07-27 16:49:37
*/
public interface SetmealService extends IService<Setmeal> {
    public void saveSetmeal(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
}
