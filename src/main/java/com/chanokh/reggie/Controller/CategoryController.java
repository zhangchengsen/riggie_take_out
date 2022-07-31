package com.chanokh.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chanokh.reggie.common.R;
import com.chanokh.reggie.entity.Category;
import com.chanokh.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增成功");
    }
    @GetMapping("/page")
    public R<Page> selectPage(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("delete_ID id:" + ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public  R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        System.out.println("修改成功");
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.eq(category.getType() != null, Category::getType, category.getType());
        qw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List list = categoryService.list(qw);
        return R.success(list);
    }


}
