package com.chanokh.reggie.service;

import com.chanokh.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ymtx
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-07-27 15:52:49
*/
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
