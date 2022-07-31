package com.chanokh.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chanokh.reggie.common.R;
import com.chanokh.reggie.dto.SetmealDto;
import com.chanokh.reggie.entity.Category;
import com.chanokh.reggie.entity.Setmeal;
import com.chanokh.reggie.service.CategoryService;
import com.chanokh.reggie.service.SetmealDishService;
import com.chanokh.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/setmeal")
@RestController
@Slf4j
public class SetmealController {
    @Autowired
    public SetmealService setmealService;
    @Autowired
    public SetmealDishService setmealDishService;
    @Autowired
    public CategoryService categoryService;
    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto) {
//        setmealService.save(setmealDto|);
        setmealService.saveSetmeal(setmealDto);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page>getPage(int page, int pageSize,String name ) {
        System.out.println(page + pageSize + name);
        Page<Setmeal> pageInfo = new Page(page, pageSize);
        Page<SetmealDto> dtoPage = new Page();

        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.isNotEmpty(name),Setmeal::getName, name);
        qw.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,qw) ;
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<SetmealDto> list = null;
        List<Setmeal> records = pageInfo.getRecords();
        list = records.stream().map(v -> {
            Long id = v.getCategoryId();
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(v,setmealDto);
            Category cate = categoryService.getById(id);
            if(cate != null)
            {
                setmealDto.setCategoryName(cate.getName());
            }

            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }
    @DeleteMapping
    //接受数组 多个或一个
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }
    @PostMapping("/status/{status}")
    public R<String> change(@PathVariable int status ,@RequestParam List<Long> ids) {
        LambdaUpdateWrapper<Setmeal> lw = new LambdaUpdateWrapper<>();
        lw.in(Setmeal::getId,ids);
        lw.set(Setmeal::getStatus,status);
        setmealService.update(null,lw);
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> list( Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId,setmeal.getCategoryId());

        qw.eq(setmeal.getStatus() != null, Setmeal::getStatus,setmeal.getStatus());
        qw.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(qw);
        return R.success(list);
    }
}
